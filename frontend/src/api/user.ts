import {rtkApi} from './config';
import {TStudent} from '../shared';

export const userApi = rtkApi.injectEndpoints({
    endpoints: (builder) => ({
        getStudents: builder.query<TStudent[], void>({
            query: () => '/users/students',
            providesTags: ['User'],
        }),
        getStudent: builder.query<TStudent, void>({
            query: (id) => `/users/students/${id}`,
            providesTags: ['User'],
        }),
        postStudent: builder.mutation<void, Partial<TStudent>>({
            query: (body) => ({
                url: '/users/students',
                method: 'POST',
                body,
            }),
            invalidatesTags: ['User'],
        })
    }),
});

export const {
    useGetStudentsQuery,
    useGetStudentQuery,
    usePostStudentMutation
} = userApi;