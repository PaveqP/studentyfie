import { rtkApi } from './config';
import { TStudent } from '../shared';

export const studentApi = rtkApi.injectEndpoints({
    endpoints: (builder) => ({
            getStudents: builder.query<TStudent[], void>({
            query: () => '/users/students',
            providesTags: ['User'],
        }),
    }),
});

export const {
    useGetStudentsQuery
} = studentApi;