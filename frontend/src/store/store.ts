import {combineReducers, configureStore} from '@reduxjs/toolkit';
import {reducer as utilReducer} from './utils.slice';

const rootReducer = combineReducers({
    util: utilReducer
});

export const store = configureStore({
    reducer: rootReducer,
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;