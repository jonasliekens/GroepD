(function($, google) {
	/*
	 *	Variables
	 */
	var map,
		directionsDisplay,
		directionsService,

		mapsCenterOnLoad = new google.maps.LatLng(51.2192159, 4.4028818), // Antwerp



	/*
	 *	jQuery objects
	 */
	 	$mapCanvas,
	 	$directionsPanel,



	/*
	 *	Functions
	 */
	 	initialize	= function() {
	 		$mapCanvas = $("#map_canvas");

			map = new google.maps.Map(
				$mapCanvas.get(0),
				{
					center:				mapsCenterOnLoad,
					zoom:				9,
					mapTypeId:			google.maps.MapTypeId.ROADMAP
				}
			);

			directionsService = new google.maps.DirectionsService()

			directionsDisplay = new google.maps.DirectionsRenderer();
			directionsDisplay.setMap(map);
			directionsDisplay.setPanel($("#directionsPanel").get(0));

			$.getJSON("rest/trip/" + $mapCanvas.data("trip-id") + "/stops", calculateRoute);

			calculateRoute();
	 	},

	 	calculateRoute	= function(data) {
	 		directionsService.route(
		 		{
		 			origin:			"Antwerpen", // LatLng
		 			destination:	"Broechem", // LatLng
		 			travelMode:		google.maps.TravelMode.WALKING
		 		},
		 		function(response, status) {
		 			if(status == google.maps.DirectionsStatus.OK) {
		 				directionsDisplay.setDirections(response);
		 			}
		 		}
	 		);
	 	};

	$(initialize);
})(window.jQuery, window.google);