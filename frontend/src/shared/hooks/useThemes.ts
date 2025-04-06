import {useEffect} from 'react';
import {setCookie, getCookie} from '../helpers';
import {useActions} from './useActions';
import { useTypedSelector } from './useTypedSelector';

export enum ECookiesTheme {
    Theme = 'theme',
    Light = 'light',
    Dark = 'dark'
}

export const useTheme = (): {toggleTheme: () => void} => {
    const {setTheme, switchTheme} = useActions();
    const {isDark} = useTypedSelector(state => state.util)

    const toggleTheme = () => {
        const newThemeName = isDark ? ECookiesTheme.Light : ECookiesTheme.Dark;
        switchTheme();
        setCookie(ECookiesTheme.Theme, newThemeName, 24 * 30);
    };

    useEffect(() => {
        const savedTheme = getCookie(ECookiesTheme.Theme);
        
        if (savedTheme) {
            const isDarkTheme = savedTheme === ECookiesTheme.Dark;
            setTheme(isDarkTheme);
        } else {
            const isDarkTheme = window.matchMedia('(prefers-color-scheme: dark)').matches;
            const themeName = isDarkTheme ? ECookiesTheme.Dark : ECookiesTheme.Light;
            setTheme(isDarkTheme);
            setCookie(ECookiesTheme.Theme, themeName, 24 * 30);
        }
    }, []);

    return {toggleTheme};
};