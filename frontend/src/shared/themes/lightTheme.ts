import type { ThemeConfig } from 'antd'

export const lightTheme: ThemeConfig = {
    token: {
        colorBgBase: '#ffffff',
        colorTextBase: '#000000',
    },
    components: {
        Button: {
            defaultBg: "transparent",
            defaultHoverBg: "#C0C0C0",
            defaultHoverColor: "#388e3c",
            defaultActiveBg: "#C0C0C0",
            defaultActiveColor: "#388e3c",
            defaultShadow: 'none',
            defaultBorderColor: 'none',
            defaultHoverBorderColor: 'none',
            defaultActiveBorderColor: 'none',
        },
        Layout: {
            headerBg: '#ffffff',
            headerColor: '#000000',
            headerPadding: '0 24px',
            siderBg: '#D3D3D3',
        },
        Input: {
            activeShadow: 'none',
            activeBorderColor: '#388e3c',
            hoverBorderColor: '#000000',
        }
    },
};