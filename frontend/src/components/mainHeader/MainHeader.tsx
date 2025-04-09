import {FC} from "react"
import {Image, Flex, Input, Divider, Button} from 'antd';
import {SearchOutlined, FilterOutlined} from '@ant-design/icons';
import logo from '../../shared/assets/images/logo.png';
import s from "./MainHeader.module.scss"

const MainHeader: FC = () => {
    return (
        <>
            <Flex align="center" justify="space-between" className={s.container}>
                <Image 
                    src={logo} 
                    width={40}
                    height={40}
                    preview={false}
                    className={s.logo}
                />
                <Flex align="center" gap={20}>
                    <Input prefix={<SearchOutlined />} placeholder="Поиск" maxLength={100} />
                    <Button icon={<FilterOutlined />} />
                </Flex>
                <Image
                    src={logo} 
                    width={40}
                    height={40}
                    preview={false}
                    className={s.logo}
                />
            </Flex>
            <Divider className={s.divider}/>
        </>
    )
}

export {MainHeader}
