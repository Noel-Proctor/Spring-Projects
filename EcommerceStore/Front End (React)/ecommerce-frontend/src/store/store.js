import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./reducers/ProductReducer";
import { errorReducer } from "./reducers/errorReducer";

const store = configureStore({
  reducer: {
    products: productReducer,
    errors: errorReducer,
  },
  preloadedState: {},
});

export default store;
