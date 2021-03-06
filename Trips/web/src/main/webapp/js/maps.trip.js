(function($, google, undefined) {
	/*
	 *	Variables
	 */
	var map,
		directionsDisplay = new google.maps.DirectionsRenderer(),
		directionsService = new google.maps.DirectionsService(),

		directionsTravelMode = google.maps.TravelMode.WALKING,
		mapsCenterLocationOnLoad = new google.maps.LatLng(51.2192159, 4.4028818), // Antwerp

		interval,
		intervalTime = 5000,

		markers = [],



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

			$.getJSON("rest/trips/" + $mapCanvas.data("trip-id") + "/stops", calculateRoute);

			// Show the locations of the users that have started the trip
			refreshParticipantsLocations();
			interval = setInterval(refreshParticipantsLocations, intervalTime);
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
				}
			);
		},

		refreshParticipantsLocations	= function() {
			$.getJSON(
				"rest/trips/participants/started",
				{
					"tripId"	: $mapCanvas.data("trip-id")
				},
				showParticipantsLocations
			);
		},

		showParticipantsLocations		= function(data) {
			var position;

			//TODO: IMPROVE
			// This is the array containing the id's of all the participants currently shown
			var participantsIds = [];
			for(var i in markers) {
				participantsIds.push(i);
			}

			for(var i = 0; i < data.length; i++) {
				if(markers[data[i].id] !== undefined) {
					participantsIds.splice(participantsIds.indexOf(data[i].id), 1);
				}	
			}

			// Loop through all the participants id's that were not in the new data and remove the marker
			for(var i = 0; i < participantsIds.length; i++) {
				markers[participantsIds[i]].setMap(null);
				markers.splice(participantsIds[i], 1);
			}
			// END IMPROVE

			for(var i = 0; i < data.length; i++) {
				// Create the position of the marker
				position = new google.maps.LatLng(data[i].latitude, data[i].longitude);

				// Create the marker if it doesn't exist yet
				if(markers[data[i].id] === undefined) {
					markers[data[i].id] = new google.maps.Marker({
						position :	position,
						map :		map ,
                        zIndex :	99
					});
				}

				// Otherwise update the position
				else {
					markers[data[i].id].setPosition(position);
				}
			}
		};

	$(initialize);
})(window.jQuery, window.google);