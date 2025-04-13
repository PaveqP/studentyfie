declare const API_URL: string;
import {BaseQueryFn, FetchArgs, createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react';

interface CustomError {
    status: number;
    data: {
        message?: string;
        result?: boolean;
    };
}

const TAG_TYPES = [
    'User',
];

export const rtkApi = createApi({
    reducerPath: 'api',
    tagTypes: TAG_TYPES,
    baseQuery: fetchBaseQuery({
        baseUrl: API_URL,
        credentials: 'include',
    }) as BaseQueryFn<string | FetchArgs, unknown, CustomError, {}>,
    endpoints: () => ({}),
});
