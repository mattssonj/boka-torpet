import {toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

// This overrides the background-colors to match Bootstrap 4
import "../ToasterStyle.css"

const toaster = {

    success: function (message) {
        toast.success(message)
    },
    error: function (message) {
        toast.error(message)
    },
    warning: function (message) {
        toast.warn(message)
    }

}

export {toaster}