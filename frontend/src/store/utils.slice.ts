import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {ThemeConfig} from 'antd';
import {darkTheme, lightTheme} from '../shared'

interface EUtilSlice {
    theme: Omit<ThemeConfig, 'components'> & {
        components?: Record<string, any>;
    };
    isDark: boolean;
}

const getInitialTheme = (): {theme: ThemeConfig, isDark: boolean;} => {
    const isDarkSystem = window.matchMedia('(prefers-color-scheme: dark)').matches;
    return {
        theme: isDarkSystem ? darkTheme : lightTheme,
        isDark: isDarkSystem
    };
};

const initialState: EUtilSlice = {
    ...getInitialTheme(),
}

export const utilSlice = createSlice({
    name: 'util',
    initialState,
    reducers: {
        switchTheme: (state) => {
            state.isDark = !state.isDark;
            state.theme = state.isDark ? darkTheme : lightTheme;
        },
        setTheme: (state, action: PayloadAction<boolean>) => {
            state.isDark = action.payload;
            state.theme = action.payload ? darkTheme : lightTheme;
        }
    }
});

export const {actions, reducer} = utilSlice;