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

  async getBookings() {
    return Axios.all([
      Axios.get("/api/bookings?onlyOngoing=true"),
      Axios.get("/api/bookings?onlyUpcoming=true"),
    ])
      .then(
        Axios.spread((ongoing, upcoming) => {
          return { ongoing: ongoing.data, upcoming: upcoming.data };
        })
      )
      .catch((error) => {
        console.log(`Error occurred when getting bookings: ${error.response}`);
        throw error;
      });
  },

  async getLastMessage() {
    return Axios.get("/api/messages/newest")
      .then((response) => response.data)
      .catch((error) => {
        return {
          message: `Kunde inte hämta nyheter. ${error}`,
          written: "",
          writer: "",
        };
      });
  },
};

export default backendClient;
