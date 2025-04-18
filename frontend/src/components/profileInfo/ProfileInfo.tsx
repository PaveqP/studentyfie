import {FC} from "react"
import {TStudent} from "shared";
import {Card, Descriptions, Row, Col, Divider, Button, Avatar, Flex} from 'antd';
import {DownloadOutlined} from '@ant-design/icons';

const mockStudentData: TStudent = {
    id: 12345,
    email: 'test@gmail.com',
    aboutMe: 'Я увлекаюсь веб-разработкой и искусственным интеллектом. Активно участвую в университетских проектах и хакатонах.',
    resumeFile: '/path/to/resume.pdf',
    firstName: 'Василий',
    surname: 'Семенов',
    lastName: 'Олегович',
    birthDate: '14-01-2004',
    learnInfo: {
        university: 'Государственный универститет аэрокосмического приборостроения',
        course: '3',
        courseName: 'Бакалавриат',
        program: 'Прикладная информатика',
        rating: 4.8,
        exchangeProgramsId: 112233
    },
    avatar: 'https://randomuser.me/api/portraits/men/1.jpg'
};

const ProfileInfo:FC = () => {
    return (
        <div style={{padding: 24}}>
            <Row gutter={[16, 16]} style={{marginTop: 20}}>
                <Flex gap={50}>
                    <Avatar
                        style={{ 
                            width: 150, 
                            height: 150, 
                            borderRadius: '50%' 
                        }}
                        src={mockStudentData.avatar}
                        onError={() => true}
                    />
                    <Card title="Основная информация" style={{flex: 1}}>
                        <Descriptions column={2}>
                            <Descriptions.Item label="Почта">{mockStudentData.email}</Descriptions.Item>
                            <Descriptions.Item label="Дата рождения">{mockStudentData.birthDate}</Descriptions.Item>
                        </Descriptions>
                    </Card>
                </Flex>
                <Divider style={{borderColor: '#7cb305'}}/>
                <Col span={24}>
                    <Card title="Обучение">
                        <Descriptions column={2}>
                            <Descriptions.Item label="Университет">{mockStudentData.learnInfo.university}</Descriptions.Item>
                            <Descriptions.Item label="Направление">{mockStudentData.learnInfo.courseName}</Descriptions.Item>
                            <Descriptions.Item label="Рейтинг">{mockStudentData.learnInfo.rating}</Descriptions.Item>
                            <Descriptions.Item label="Курс">{mockStudentData.learnInfo.course}</Descriptions.Item>
                            <Descriptions.Item label="Программа">{mockStudentData.learnInfo.program}</Descriptions.Item>
                        </Descriptions>
                    </Card>
                </Col>
                <Divider style={{borderColor: '#7cb305'}}/>
                <Col span={24}>
                    <Card title="О себе">{mockStudentData.aboutMe}</Card>
                </Col>
                <Divider style={{borderColor: '#7cb305'}}/>
                <Col span={24}>
                    <Card title="Резюме">
                        <Button icon={<DownloadOutlined />} href={mockStudentData.resumeFile}>
                            Скачать резюме
                        </Button>
                    </Card>
                </Col>
            </Row>
        </div>
        )
}

export {ProfileInfo}
