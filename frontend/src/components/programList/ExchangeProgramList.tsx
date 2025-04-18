import {FC, useState} from "react"
import {List, Card, Pagination, Rate, Row, Col, Avatar, Button} from 'antd';

export type TExchangeProgram = {
    id: number;
    image: string;
    programName: string;
    university: string;
    description: string;
    rating: number;
    details: string;
};

const mockPrograms: TExchangeProgram[] = Array.from({ length: 50 }, (_, i) => ({
    id: i + 1,
    image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/%D0%93%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5_%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5_%D0%93%D0%A3%D0%90%D0%9F.jpg/1200px-%D0%93%D0%BB%D0%B0%D0%B2%D0%BD%D0%BE%D0%B5_%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5_%D0%93%D0%A3%D0%90%D0%9F.jpg',
    programName: `Международная программа обмена ${i + 1}`,
    university: `Университет ${String.fromCharCode(65 + (i % 26))}`,
    description: 'Уникальная возможность обучения в ведущем вузе мира',
    details: `Программа включает: 
    - Стажировку в международных компаниях
    - Изучение 2 иностранных языков
    - Культурные обмены
    - Научно-исследовательские проекты`,
    rating: Number((Math.random() * 4 + 1).toFixed(1)),
}));

const ExchangeProgramList: FC = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const pageSize = 6;

    return (
        <div style={{ padding: 24, maxWidth: 1200, margin: '0 auto' }}>
            <List
                    grid={{ gutter: 16, column: 2 }}
                    dataSource={mockPrograms.slice(
                    (currentPage - 1) * pageSize,
                    currentPage * pageSize
                )}
                renderItem={(program) => (
                    <List.Item>
                        <Card
                            cover={
                                <img
                                alt={program.programName}
                                src={program.image}
                                style={{ height: 200, objectFit: 'cover' }}
                                />
                            }
                            actions={[
                                <Rate 
                                    disabled 
                                    allowHalf 
                                    value={program.rating} 
                                    tooltips={['1', '2', '3', '4', '5']}
                                />,
                                <Button>Подробнее</Button>
                            ]}
                        >
                        <Card.Meta
                            avatar={<Avatar src={program.image} />}
                            title={program.programName}
                            description={
                                <>
                                    <Row gutter={[8, 8]}>
                                    <Col span={24}>
                                        <strong>Университет:</strong> {program.university}
                                    </Col>
                                    <Col span={24}>
                                        <strong>Рейтинг:</strong> {program.rating}/5
                                    </Col>
                                    <Col span={24}>
                                        <strong>Описание:</strong> {program.description}
                                    </Col>
                                    <Col span={24}>
                                        <div style={{ marginTop: 12 }}>
                                        {program.details.split('\n').map((line, i) => (
                                            <div key={i}>{line.trim()}</div>
                                        ))}
                                        </div>
                                    </Col>
                                    </Row>
                                </>
                            }
                            
                        />
                        </Card>
                    </List.Item>
                )}
            />
            <div style={{ textAlign: 'center', marginTop: 24 }}>
                <Pagination
                current={currentPage}
                total={mockPrograms.length}
                pageSize={pageSize}
                onChange={(page) => setCurrentPage(page)}
                showSizeChanger={false}
                />
            </div>
        </div>
    );
};

export {ExchangeProgramList}
