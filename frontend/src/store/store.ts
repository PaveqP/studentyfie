import {combineReducers, configureStore} from '@reduxjs/toolkit';
import {reducer as utilReducer} from './utils.slice';
import {rtkApi} from '../api';

const rootReducer = combineReducers({
    util: utilReducer,
    [rtkApi.reducerPath]: rtkApi.reducer
});

export const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(rtkApi.middleware)
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;