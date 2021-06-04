import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

// This overrides the background-colors to match Bootstrap 4
import "./ToasterStyle.css";

// https://github.com/fkhadra/react-toastify#readme for more information

const toaster = {
  success(message) {
    toast.success(message);
  },
  error(message) {
    toast.error(message);
  },
  warning(message) {
    toast.warn(message);
  },
};

export { toaster };
