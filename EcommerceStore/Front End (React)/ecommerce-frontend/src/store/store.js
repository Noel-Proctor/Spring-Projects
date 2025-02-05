import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./reducers/ProductReducer";

const store = configureStore({
  reducer: {
    products: productReducer,
  },
  preloadedState: {},
});

export default store;
