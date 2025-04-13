export type TStudent = {
    id: number,
    email: string,
    aboutMe: string,
    resumeFile: string,
    firstName: string,
    surname: string,
    lastName: string,
    birthDate: string,
    learnInfo: {
        university: string,
        course: string,
        courseName: string,
        program: string,
        rating: number,
        exchangeProgramsId: number
    },
    avatar: string
}