import Axios from "axios";
import toaster from "./Toaster";

const backendClient = {
  async getCurrentLoggedInUser() {
    return Axios.get("/api/users/current")
      .then((response) => response.data)
      .catch((error) => {
        toaster.error(
          `Fel när användarinformation skulle hämtas: ${error.response.data.message}`
        );
        throw error;
      });
  },
};

export { backendClient as default };
