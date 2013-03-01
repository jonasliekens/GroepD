(function($, google, undefined) {
	/*
	 *	Variables
	 */
	var map,
		marker,



	/*
	 *	jQuery objects
	 */
		$mapCanvas,
		$inputLatitude,
		$inputLongitude,


	/*
	 *	Functions
	 */
		initialize	= function() {
			$mapCanvas = $("#map_canvas");
			$inputLatitude = $("#latitude");
			$inputLongitude = $("#longitude");

			map = new google.maps.Map(
				$mapCanvas.get(0),
				{
					center:				new google.maps.LatLng(51.2192159, 4.4028818),
					zoom:				9,
					mapTypeId:			google.maps.MapTypeId.ROADMAP
				}
			);

			google.maps.event.addListener(map, "click", mapClicked);
		},

		showMarkerPosition	= function(latLng) {
			$inputLatitude.val(latLng.lat());
			$inputLongitude.val(latLng.lng());
		},


	/*
	 *	Event listeners
	 */
		mapClicked	= function(e) {
			// Create the marker if it doesn't exist yet
			if(marker === undefined) {
				marker = new google.maps.Marker({
					position: e.latLng,
					map: map,
					draggable: true,
					animation: google.maps.Animation.DROP
				});

				google.maps.event.addListener(marker, "dragend", positionChanged);
				google.maps.event.addListener(marker, "drag", positionChanged);

				showMarkerPosition(e.latLng);
			}

			// Otherwise, change its position
			else {
				marker.setPosition(e.latLng);
				showMarkerPosition(e.latLng);
			}
		},

		positionChanged	= function(e) {
			showMarkerPosition(e.latLng);
		};

	$(initialize);
})(window.jQuery, window.google);