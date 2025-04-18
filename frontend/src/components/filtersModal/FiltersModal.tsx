import { FC } from 'react';
import { 
    Modal, 
    Checkbox, 
    DatePicker, 
    Select, 
    Slider, 
    Space, 
    Button,
    Radio,
    InputNumber
} from 'antd';

const { RangePicker } = DatePicker;
const { Option } = Select;

type FilterPopupProps = {
    visible: boolean;
    onClose: () => void;
};

const FiltersModal: FC<FilterPopupProps> = ({ visible, onClose }) => {
    const categories = [
        { label: 'Технические', value: 'tech' },
        { label: 'Гуманитарные', value: 'humanitarian' },
        { label: 'Творческие', value: 'creative' },
    ];

    const statusOptions = [
        { label: 'Открытые', value: 'open' },
        { label: 'Завершенные', value: 'closed' },
        { label: 'Предстоящие', value: 'upcoming' },
    ];

    const sortOptions = [
        { value: 'rating', label: 'По рейтингу' },
        { value: 'date', label: 'По дате' },
        { value: 'participants', label: 'По количеству участников' },
    ];

    return (
        <Modal
        title="Расширенные фильтры и сортировка"
        open={visible}
        onCancel={onClose}
        footer={[
            <Button key="reset" danger onClick={onClose} style={{border: "none", boxShadow: "none"}}>
                Сбросить
            </Button>,
            <Button key="apply" onClick={onClose}>
                Применить
            </Button>,
        ]}
        width={800}
        >
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
            <div>
                <h4>Сортировка</h4>
                <Select defaultValue="rating" style={{ width: '100%' }}>
                    {sortOptions.map(option => (
                    <Option key={option.value} value={option.value}>
                        {option.label}
                    </Option>
                    ))}
                </Select>
            </div>
            <div>
                <h4>Дата проведения</h4>
                <RangePicker showTime style={{ width: '100%' }} />
            </div>
            <div>
                <h4>Категории программ</h4>
                <Checkbox.Group options={categories} />
            </div>
            <div>
                <h4>Статус программ</h4>
                <Checkbox.Group options={statusOptions} />
            </div>
            <div>
                <h4>Рейтинг программы</h4>
                <Slider
                    range
                    min={1}
                    max={5}
                    step={0.5}
                    defaultValue={[2, 4]}
                    marks={{
                    1: '1',
                    3: '3',
                    5: '5',
                    }}
                />
            </div>
            <div>
                <h4>Количество участников</h4>
                <Space>
                    От <InputNumber min={0} max={1000} defaultValue={10} />
                    До <InputNumber min={0} max={1000} defaultValue={100} />
                </Space>
            </div>
            <div>
                <h4>Формат участия</h4>
                <Radio.Group defaultValue="any">
                    <Radio value="any">Любой</Radio>
                    <Radio value="online">Онлайн</Radio>
                    <Radio value="offline">Оффлайн</Radio>
                </Radio.Group>
            </div>
        </Space>
        </Modal>
    );
};

export {FiltersModal}