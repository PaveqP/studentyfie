import Cookies from 'universal-cookie';

const cookies = new Cookies();

export function setCookie(name: string, value: string, expiresInHours?: number): void {
    if (expiresInHours) {
        const expirationDate = new Date();
        let time = expirationDate.getTime();
        time += expiresInHours * 60 * 60 * 1000;
        expirationDate.setTime(time);
        cookies.set(name, value, {expires: expirationDate});
        return;
    }
    cookies.set(name, value);
}

export function getCookie(name: string): string | undefined {
    return cookies.get(name);
}

export const deleteCookie = (name: string): void => {
    if (getCookie(name)) cookies.remove(name);
};