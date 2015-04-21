function facebookLogin() {
	FB.login(function(response) {
		if (response.authResponse) {
			console.log('Welcome! Fetching your information');
			FB.api('/me', function(response) {
				console.log('Good to see you, ' + response.name + '.');
				sendLogin(response.name, response.id);
			});
			console.log(response);
		} else {
			console.log('User cancelled login or did not fully authorize.');
		}
	});
}

function sendLogin(name, id) {
	$.ajax({
		type : 'post',
		url : '/Login',
		data : {
			'name' : name,
			'id' : id
		},
		success : function(data) {
			document.location.href = "mainPage.jsp";
		},
		error : function() {
			alert('error');
		}
	});
}

function initializeNow() {
	var defaultLatLng = new google.maps.LatLng(34.0983425, -118.3267434); // Default to Hollywood, CA when no geolocation support
	if (navigator.geolocation) {
		function success(pos) {
			// Location found, show map with these coordinates
			drawMap(new google.maps.LatLng(pos.coords.latitude,
					pos.coords.longitude));
		}
		function fail(error) {
			drawMap(defaultLatLng); // Failed to find location, show default map
		}
		// Find the users current position.  Cache the location for 5 minutes, timeout after 6 seconds
		navigator.geolocation.getCurrentPosition(success, fail, {
			maximumAge : 500000,
			enableHighAccuracy : true,
			timeout : 6000
		});
	} else {
		drawMap(defaultLatLng); // No geolocation support, show default map
	}
}

function drawMap(latlng) {
	position = latlng;
	var myOptions = {
		zoom : 12,
		center : latlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("map-canvas"),
			myOptions);
	// Add an overlay to the map of current lat/lng
	var marker = new google.maps.Marker({
		position : latlng,
		map : map,
		title : "Greetings!"
	});
}

function initializeWeather() {

	var mapOptions = {
		zoom : 12,
		center : position
	};

	var map = new google.maps.Map(document.getElementById('map-canvas'),
			mapOptions);

	var weatherLayer = new google.maps.weather.WeatherLayer({
		temperatureUnits : google.maps.weather.TemperatureUnit.CELSIUS
	});
	weatherLayer.setMap(map);

	var cloudLayer = new google.maps.weather.CloudLayer();
	cloudLayer.setMap(map);

	var marker = new google.maps.Marker({
		map : map,
		position : position
	});

};

function initializePlaces() {
	var pyrmont = position;

	map = new google.maps.Map(document.getElementById('map-canvas'), {
		center : pyrmont,
		zoom : 15
	});

	var request = {
		location : pyrmont,
		radius : 500,
		types : [ 'store' ]
	};
	infowindow = new google.maps.InfoWindow({
		map : map,
		position : pyrmont,
		content : 'You are here.'
	});

	var service = new google.maps.places.PlacesService(map);
	service.nearbySearch(request, callback);
};

function callback(results, status) {
	if (status == google.maps.places.PlacesServiceStatus.OK) {
		for (var i = 0; i < results.length; i++) {
			createMarker(results[i]);
		}
	}
};

function createMarker(place) {
	var placeLoc = place.geometry.location;
	var marker = new google.maps.Marker({
		map : map,
		position : place.geometry.location
	});

	google.maps.event.addListener(marker, 'click', function() {
		infowindow.setContent(place.name);
		infowindow.open(map, this);
		selectedPlace = place.geometry.location;
		$('#directions').closest('.ui-btn').show();
	});

	google.maps.event.addListener(infowindow, 'closeclick', function() {
		$('#directions').closest('.ui-btn').hide();
	});
};

function getDirections() {
	var url = "https://maps.google.com/maps?saddr=My+Location&daddr="
			+ selectedPlace;

	var win = window.open(url, '_blank');
	win.focus();
}

function saveLocation() {
	$.ajax({
		type : 'post',
		url : '/Location',
		data : {
			'latitude' : position.k,
			'longitude' : position.D
		},
		success : function(data) {
			alert("Location saved sucessfully.");
		},
		error : function() {
			alert('error');
		}
	});
}

function createMyPlaces() {
	$.ajax({
		type : 'get',
		url : '/Location',
		data : {},
		success : function(data) {
			myPlacesMarkers(data);
		},
		error : function() {
			alert('error');
		}
	});
}

function myPlacesMarkers(markers) {

	var map;
	var bounds = new google.maps.LatLngBounds();
	var mapOptions = {
		mapTypeId : 'roadmap'
	};

	// Display a map on the page
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	//map.setTilt(45);

	// Display multiple markers on a map
	var infoWindow = new google.maps.InfoWindow(), marker, i;

	// Loop through our array of markers & place each one on the map  
	for (i = 0; i < markers.length; i++) {
		var position = new google.maps.LatLng(parseFloat(markers[i].latitude),
				parseFloat(markers[i].longitude));
		bounds.extend(position);
		marker = new google.maps.Marker({
			position : position,
			map : map
		});
		// Allow each marker to have an info window    
		google.maps.event.addListener(marker, 'click', (function(marker, i) {
			return function() {
				infoWindow.open(map, marker);
			}
		})(marker, i));

		// Automatically center the map fitting all markers on the screen

	}

	map.fitBounds(bounds);

	// Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
	var boundsListener = google.maps.event.addListener((map), 'idle', function(
			event) {
		map.setZoom(3);
		google.maps.event.removeListener(boundsListener);
	});

}