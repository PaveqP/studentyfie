import {ThemeConfig} from 'antd';

export const darkTheme: ThemeConfig = {
    token: {
        colorBgBase: '#202020',
        colorTextBase: '#ffffff',
    },
    components: {
        Button: {
            defaultBg: "transparent",
            defaultHoverBg: "#696969",
            defaultHoverColor: "#81c784",
            defaultActiveBg: "#696969",
            defaultActiveColor: "#81c784",
            defaultShadow: 'none',
            defaultBorderColor: 'none',
            defaultHoverBorderColor: 'none',
            defaultActiveBorderColor: 'none',
        },
        Layout: {
            headerBg: '#202020',
            headerColor: '#ffffff',
            headerPadding: '0 24px',
            siderBg: '#2F2F2F',
        },
        Input: {
            activeShadow: 'none',
            activeBorderColor: '#81c784',
            hoverBorderColor: '#ffffff',
        }
    },
};