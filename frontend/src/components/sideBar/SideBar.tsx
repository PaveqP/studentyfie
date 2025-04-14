import {Button, Flex} from "antd";
import {MenuOutlined, HomeOutlined, LoginOutlined, LogoutOutlined, UserOutlined, SunFilled, MoonFilled} from "@ant-design/icons"
import {FC, useEffect} from "react"
import s from "./SideBar.module.scss"
import {useNavigate} from "react-router-dom"
import {ERoutes} from "../../app"
import {useTheme, useTypedSelector} from "../../shared"
import cn from "classnames"
import { useGetStudentsQuery, usePostStudentMutation } from "../../api";

interface ISideBarProps {
    collapsed: boolean;
    setCollapsed: (collapsed: boolean) => void;
}

const SideBar: FC<ISideBarProps> = ({collapsed, setCollapsed}) => {
    const navigate = useNavigate();
    const {toggleTheme} = useTheme();
    const isDark = useTypedSelector(state => state.util.isDark);
    const [createStudent] = usePostStudentMutation();
    // const test = () => {
    //     createStudent({email: 'ivaso2004@mail.ru', firstName: 'Василий', lastName: 'Semenov', birthDate: '14.01.2004', learnInfo: {
    //         university: 'ГУАП',
    //         course: '3',
    //         courseName: 'Бакалавриат',
    //         program: 'Прикладная информатика',
    //         rating: 4.5,
    //         exchangeProgramsId: 1
    //     }});
    // }

    return (
        <Flex vertical align="center" justify="space-between" className={cn(s.container, {
            [s.containerDark]: isDark
        })}>
            <Flex vertical align="center" gap={20} className={s.flex}>
                <Button
                    onClick={() => setCollapsed(!collapsed)}
                    icon={<MenuOutlined />}
                    className={s.button}
                />
                <Button
                    onClick={() => navigate(ERoutes.Main)}
                    icon={<HomeOutlined />}
                    className={s.button}
                >
                    {!collapsed && <>Главная</>}
                </Button>
                <Button
                    onClick={toggleTheme}
                    icon={isDark ? <MoonFilled /> : <SunFilled />}
                    className={s.button}
                >
                    {!collapsed && <>{isDark ? "Темная" : "Светлая"}</>}
                </Button>
            </Flex>
            <Flex vertical align="center" gap={20} className={s.flex}>
                <Button
                    onClick={() => navigate(ERoutes.Profile)}
                    icon={<UserOutlined />}
                    className={s.button}
                >
                {!collapsed && <>Профиль</>}
            </Button>
                <Button icon={<LoginOutlined />} className={s.button}>
                {!collapsed && <>Войти</>}
            </Button>
                <Button icon={<LogoutOutlined />} className={s.button}>
                {!collapsed && <>Выйти</>}
            </Button>
            </Flex>
        </Flex>
    )
}

export {SideBar}
