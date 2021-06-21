import Axios from "axios";
import toaster from "../Toaster";

const messageClient = {
  async getLastMessage() {
    return Axios.get("/api/messages/newest")
      .then((response) => response.data)
      .catch((error) => ({
        message: `Kunde inte hämta nyheter. ${error}`,
        written: "",
        writer: "",
      }));
  },

  async getMessagePage(page, size) {
    return Axios.get("/api/messages", {
      params: {
        page,
        size,
      },
    })
      .then((response) => ({
        content: response.data.content,
        isLast: response.data.last,
      }))
      .catch((error) => {
        toaster.error(
          `Kunde inte hämta meddelanden. Error: ${error.response.data.message}`
        );
        throw error;
      });
  },
};

export default messageClient;
