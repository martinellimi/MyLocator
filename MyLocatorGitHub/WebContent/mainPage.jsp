<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" type="text/css" href="styles/styles.css">
<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?sensor=false&libraries=weather,places"></script>
<script src="scripts/scripts.js"></script>
<style>

</style>
<script type="text/javascript">
	var position;
	$(document).on("pageinit", "#map-page", function() {
		initializeNow();
	});

	var map;
	var infowindow;
	var myplaces = false;
	var selectedPlace;

	$(document).ready(function() {

		$('#directions').closest('.ui-btn').hide();

		$("#now").click(function() {
			initializeNow();
		});

		$("#save").click(function() {
			saveLocation();
		});

		$("#myplaces").click(function() {
			$('#places').hide();
			$('#weather').hide();
			$('#now').hide();
			$('#save').hide();
			$('#directions').hide();
			if (myplaces) {
				//createMyPlaces();
			} else {
				myplaces = true;
				createMyPlaces();
			}
		});

		$("#directions").click(function() {
			getDirections();
		});
		
		$("#placesnow").click(function() {
			$('#places').show();
			$('#weather').show();
			$('#now').show();
			$('#save').show();
			$('#directions').show();
		});
		
		$("#places").click(function() {
			initializePlaces();
		});

		$("#weather").click(function() {
			initializeWeather();
		});
	});
</script>

</head>
<body>
	<div data-role="page" id="map-page" data-url="map-page">
		<div data-role="header" data-theme="a">
			<h1>Maps</h1>
		</div>
		<div role="main" class="ui-content" id="test">
			<div data-role="tabs" id="tabs">
				<div data-role="navbar">
					<ul>
						<li id="placesnow"><a href="#one" data-ajax="false">Now</a></li>
						<li id="myplaces"><a href="#two" data-ajax="false">My
								Places</a></li>
					</ul>
				</div>
				<div id="one">
					<div id="map-canvas"></div>
					<!-- map loads here... -->
				</div>
				<div id="two">
					<div id="map_canvas"></div>
					<!-- map loads here... -->
				</div>
			</div>

		</div>
		<div data-role="footer" >
			<div id="footer" data-role="controlgroup" data-type="horizontal"
				style="display: block; text-align: center;">
				<a href="#" class="ui-btn ui-corner-all" id="places">Places</a> 
				<a href="#" class="ui-btn ui-corner-all" id="weather">Weather</a> 
				<a href="#" class="ui-btn ui-corner-all" id="now">Now</a> 
				<a href="#" class="ui-btn ui-corner-all" id="save">Save</a> 
				<input type="button" id="directions" name="button1" value="Directions" />
			</div>
		</div>
	</div>

</body>
</html>