import React from 'react';
import {toast} from "react-toastify";

const success = (message) => {
    toast.success(message)
}

const warning = (message) => {
    toast.warn(message)
}

const error = (message) => {
    toast.error(message)
}

export {success, warning, error}