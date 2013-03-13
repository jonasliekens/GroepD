/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 12/03/13
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
function doConfirm(id) {
    var input = "id=" + id;
    $.post(
        "rest/broadcast/confirm",
        input,
        function (data) {
            if (data == "true") {
                window.location = "broadcast";
            }
        }
    );
}