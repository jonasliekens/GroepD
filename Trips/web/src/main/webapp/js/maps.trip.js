(function($, google) {
	/*
	 *	Variables
	 */
	var map,
		directionsDisplay = new google.maps.DirectionsRenderer(),
		directionsService = new google.maps.DirectionsService(),

		directionsTravelMode = google.maps.TravelMode.WALKING,
		mapsCenterLocationOnLoad = new google.maps.LatLng(51.2192159, 4.4028818), // Antwerp



	/*
	 *	jQuery objects
	 */
		$mapCanvas,
		$mapError,



	/*
	 *	Functions
	 */
		initialize	= function() {
			$mapCanvas = $("#map_canvas");
			$mapError = $("#map_error");

			map = new google.maps.Map(
				$mapCanvas.get(0),
				{
					center:				mapsCenterLocationOnLoad,
					zoom:				9,
					mapTypeId:			google.maps.MapTypeId.ROADMAP
				}
			);

			directionsDisplay.setMap(map);
			directionsDisplay.setPanel($("#directionsPanel").get(0));

			console.log("rest/trips/" + $mapCanvas.data("trip-id") + "/stops");
			$.getJSON("rest/trips/" + $mapCanvas.data("trip-id") + "/stops", calculateRoute);
		},

		calculateRoute	= function(stops) {
			$mapError.toggle(stops.length < 2);

			// Don't try to calculate a route when there are less than 2 stops
			if(stops.length < 2) {
				return false;
			}

			// Get and remove the first and last element from the stops
			var first = stops.shift(),
				last = stops.pop(),
				otherStops = [];

			// Loop through the stops in between and make waypoints of them
			for(var key in stops) {
				otherStops.push({
					location	: new google.maps.LatLng(stops[key].latitude, stops[key].longitude),
					stopover	: true
				});
			}

			// Let Google calculate the route
			directionsService.route(
				{
					origin:			new google.maps.LatLng(first.latitude, first.longitude),
					destination:	new google.maps.LatLng(last.latitude, last.longitude),
					waypoints:		otherStops,
					travelMode:		directionsTravelMode
				},
				function(response, status) {
					if(status === google.maps.DirectionsStatus.OK) {
						directionsDisplay.setDirections(response);
					}

					console.log(status);
				}
			);
		};

	$(initialize);
})(window.jQuery, window.google);