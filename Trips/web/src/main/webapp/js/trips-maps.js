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



	/*
	 *	Functions
	 */
		initialize	= function() {
			$mapCanvas = $("#map_canvas");

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

			//$.getJSON("rest/trip/" + $mapCanvas.data("trip-id") + "/stops", calculateRoute);

			calculateRoute([
				{
					"id"			: 1,
					"name"			: "Some blah blah stop",
					"description"	: "Some description",
					"latitude"		: 51.2192159,
					"longitude"		: 4.4028818,
					"accuracy"		: 5,
					"orderNumber"	: 1
				},
				{
					"id"			: 2,
					"name"			: "Some blah blah stop 2",
					"description"	: "Some description 2",
					"latitude"		: 51.2192159,
					"longitude"		: 5.4028818,
					"accuracy"		: 5,
					"orderNumber"	: 2
				},
				{
					"id"			: 3,
					"name"			: "Some blah blah stop 3",
					"description"	: "Some description 3",
					"latitude"		: 50.2192159,
					"longitude"		: 5.4028818,
					"accuracy"		: 5,
					"orderNumber"	: 2
				}
			]);
		},

		calculateRoute	= function(stops) {
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
// LATLNG CAUSES ZERO RESULTS
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
				}
			);
		};

	$(initialize);
})(window.jQuery, window.google);