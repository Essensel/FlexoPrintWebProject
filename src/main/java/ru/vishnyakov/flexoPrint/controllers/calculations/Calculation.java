package ru.vishnyakov.flexoPrint.controllers.calculations;

import ru.vishnyakov.flexoPrint.controllers.beens.Order;

import java.util.HashMap;

;

public class Calculation {
    private final double ue = 60.01; //курс уе
    private final double circumference = 0.3302; //длина окружности вала в м. для расчета взято среднее значение по умолчанию. вал 13дюймлв
    private final int number_of_revolutions = 75; // число оборотов формного вала, требуемое для настройки 1 секции печати.
    private final int maxWidht = 330;// максимальная ширина печати в мм.(технологическая характеристика используемых печатных машин )
    private final int printWidth = 310; // масимальная ширина полотна минус технологические отступы по 10мм с 2х сторон.
    private final double paintConsumptionOnMeter = 1.0; // г. усреднённое значение расхода КРАСКИ на 1метр квадратный печати.
    private final double varnishConsumptionOnMeter = 1.0; // г. усреднённое значение расхода ЛАКА на 1метр квадратный печати.
    private final double paintPrice = ue * (32.5) / 1000; //цена краски за 1 г. (взято среднее значение)
    private final double coldFoilPrice = 5000;
    private final double hotFoilPrice = 3003;
    /**
     * Расчет расхода краски на тираж
     *
     * @param materialConsumption расход материала в метрах квадратных
     * @return
     */
    public double getPaintConsumption(double materialConsumption) {
        return paintConsumptionOnMeter * materialConsumption;
    }

    /**
     * Расчет расхода лака на тираж
     *
     * @param materialConsumption расход материала в метрах квадратных
     * @return
     */
    public double getVarnishConsumption(double materialConsumption) {
        return varnishConsumptionOnMeter * materialConsumption;
    }

    public double getPrimeCostOfCirculation(Order curentOrder) {
        double primeCostOfCirculation = -1.0;
        if (curentOrder != null) {
            double materialConsumption = getMaterialConsumption(curentOrder.getOrderSize(), curentOrder.getWidth(), curentOrder.getLength(), curentOrder.getColors());
            double materialPrice = getMaterialPrice(curentOrder.getMaterialType());
            double paintConsumption = getPaintConsumption(materialConsumption);
            double paintPrice = getPaintPrice(curentOrder.getColors());
            double varnishConsumption = getVarnishConsumption(materialConsumption);
            double varnishPrice = getVarnishPrice(curentOrder.getVarnish());
            double additionalProcessingPrice = getAdditionalProcessingPrice(curentOrder.getProcessing(),curentOrder.getWidth());
            primeCostOfCirculation = (materialConsumption * materialPrice) + (paintConsumption * paintPrice) + (varnishConsumption * varnishPrice)
                    + (materialConsumption*getAdditionalProcessingPrice(curentOrder.getProcessing(),curentOrder.getWidth()));
        }

        return primeCostOfCirculation;
    }

    /**
     * Расчет стоимости доп обработки
     * @param key виды доп обрабоки вводятся пользователем сохраняются в 1 строке. каждое слово разделённое пробелом уникально,
     * представляет отдельный вид допобработки, цены на каждую из них отличаются. В каждом тираже может быть до 7 видов различных доп обработок.
     * @return цена доп обработки на 1 м квадратный печати.
     */
    public double getAdditionalProcessingPrice(String key, int labelWight) {

        double additioanlProcessingPrice = -1.0;
        if (key != null) {
        }
        if (key.equals("") || key.equals(" ")) {
            additioanlProcessingPrice = 0.0; //ксли доп обработки нет цена 0
        } else {
            key.trim();
            double materialWidght = getMaterialWidght(labelWight);
            additioanlProcessingPrice = 0.0;
            // в зависимости от вида доп обработки, который хранится в переменной key
            for (String retval : key.split(" ")) {
                switch (retval) {
                    case "coldFoil":
                        additioanlProcessingPrice += (coldFoilPrice/materialWidght); // цена за 1 м.кв. = средняя цена за 1 стандартный ролик фольги хол. тиснения в 2000 метров погонных / на ширину материала конкретного тиража.
                        break;
                    case "hotFoil":
                        additioanlProcessingPrice += (hotFoilPrice/materialWidght);
                        break;
                    case "lamination":
                        additioanlProcessingPrice += 3;
                        break;
                    case "relamDelam":
                        additioanlProcessingPrice += 4;
                        break;
                    case "congrev":
                        additioanlProcessingPrice += 5;
                        break;
                    case "traf":
                        additioanlProcessingPrice += 6;
                        break;
                    case "special":
                        additioanlProcessingPrice += 7;
                        break;
                }
            }
        }
        return additioanlProcessingPrice;
    }


    private double getPaintPrice(String colorsText) {
        double paintPrice = 0.0;
        int colors = Integer.parseInt(colorsText);
        paintPrice = colors * paintPrice;
        return paintPrice;
    }

    private double getVarnishPrice(String key) {
        double resultValue = -1.0;
        if (!key.equals(" ") && !key.equals("") && key != null) {
            HashMap<String, Double> materialPrice = new HashMap<>();
            materialPrice.put("Без лака", 0.0);
            materialPrice.put("Глянцевый лак", 31.54);
            materialPrice.put("Матовый лак", 39.01);
            materialPrice.put("Глянцевый лак + матовый лак", 49.43);
            materialPrice.put("Технолак глянцевый", 49.07);
            materialPrice.put("Структурный лак матовый", 51.65);
            materialPrice.put("Структурный лак матовый + глянцевый лак", 62.92);
            resultValue = materialPrice.get(key);
        }
        return resultValue;
    }


    private double getMaterialPrice(String key) {
        double resultValue = -1.0;
        if (!key.equals(" ") && !key.equals("") && key != null) {
            HashMap<String, Double> materialPrice = new HashMap<>();
            materialPrice.put("Бумага полуглянцевая (акриловый клей - стандартный)", 31.21);
            materialPrice.put("Бумага полуглянцевая (каучуковый клей - морозостойкий)", 31.54);
            materialPrice.put("Бумага полуглянцевая (усиленный клей - для глубокой заморозки C2075", 39.01);
            materialPrice.put("Плёнка полипропиленовая белая (акриловый клей - стандартный)", 49.43);
            materialPrice.put("Плёнка полипропиленовая белая (каучуковый клей - морозостойкий)", 49.07);
            materialPrice.put("Плёнка полипропиленовая прозрачная", 51.65);
            materialPrice.put("Плёнка полипропиленовая суперпрозрачная", 62.92);
            materialPrice.put("Плёнка полиэтиленовая PE85 (белая)", 55.47);
            materialPrice.put("Плёнка полиэтиленовая PE100 (белая)", 56.01);
            materialPrice.put("Термобумага ТОП", 47.69);
            materialPrice.put("Термобумага ЭКО", 34.66);
            materialPrice.put("Пленка металлизированная", 65.90);
            materialPrice.put("Бумага металлизированная", 65.08);
            materialPrice.put("Бумага дизайнерская Ant cream клей акриловый", 74.28);
            materialPrice.put("Бумага дизайнерская Fmb клей резиновый", 80.08);
            resultValue = materialPrice.get(key);
        }
        return resultValue;
    }

    /**
     * @param circulationSizeInlabel размер тиража в штуках этикеток
     * @param labelWidth             // ширина этикетки
     * @param labelLength            // длина этикетки
     * @return
     */
    private Double getCirculationSizeInSquareMeters(Long circulationSizeInlabel, int labelWidth, int labelLength) {
        Double circulationSize = (circulationSizeInlabel * ((labelWidth + 6.0) * (labelLength + 6.0))) / 1000000; //добавляем по 6 мм к длине и ширине этикетки
        // для учета минимального межэтикеточного пространства (по 3мм с каждой стороны).
        // умножая длину этикетки на её ширину получаем площадь этикетки и умножая площадь на количество этикеток получаем размер тиража.
        // Делим на 1 000 000 для перевода в квадратные метры
        return circulationSize;

    }

    /**
     * Метод производит расчет брака в процентном соотношении от объема тиража в зависимости от количества красок.
     *
     * @param colors количество используемых красок в конкретном тираже, вводится пользователем.
     * @return брак в процентном соотношении от объема тиража.
     */
    private double getDefectiveMaterialSize(String colors) {
        int colorsCount = Integer.parseInt(colors);
        double defectiveMaterialSize = (colorsCount * 0.4) + 1; // коэффициент расчитан технологом производства

        return defectiveMaterialSize / 100.0; // делим на 100 для расчета в%
    }

    /**
     * Метод для расчёта необходимой длины материала для настройки тиража (рачет в метрах погонных).
     *
     * @param colors количество цветов конкретного тиража.
     * @return settingLength - длина настройки в м.
     */
    private double getSettingLength(String colors) {
        Integer colorsCount = Integer.parseInt(colors);
        double settingLength = ((colorsCount * number_of_revolutions) + number_of_revolutions) * circumference; // Расчет длины материала в м, требуемого для настроики тиража.
        // Количество секций печати равно количеству цветов в тираже + настройка вырубной секции
        return settingLength;
    }

    /**
     * расход запечатываемого материала = (размер тиража в квадратных метрах+(длина настройки*ширина материала))/(1-брак)
     *
     * @param circulationSizeInlabel размер тиража количество этикеток
     * @param labelWidth             ширина этикетки
     * @param labelLength            длина этикетки
     * @param colors                 количество цветов печати
     * @return
     */
    public double getMaterialConsumption(Long circulationSizeInlabel, int labelWidth, int labelLength, String colors) {
    /*    Double circulationSize; // размер тиража в квадратных метрах
        Double settingLength; // длина настройки
        Double materialWidth; // ширина материала
        Double defectiveMaterial; // брак*/

        Double circulationSize = getCirculationSizeInSquareMeters(circulationSizeInlabel, labelWidth, labelLength);
        double settingLength = getSettingLength(colors);
        double materialWidth = getMaterialWidght(labelWidth);
        double defectiveMaterial = getDefectiveMaterialSize(colors);
        Double materialConsumption = (circulationSize + (settingLength * (materialWidth/1000))) / (1 - defectiveMaterial); // ширину материала в мм делим на 1000 для перевода в метры
        return materialConsumption;
    }

    /**
     * метод определяет максимальную ширину печати
     * общая ширина полотна не может превышать  maxWidht = 330мм (по технологическим особенностям)
     * с каждых концов материала необходимо оставлять по 10мм на технологичекий отступ(итого 20м)
     * к ширине этикетки, задаваемой клиентом, необходимо добавлять минимум по 3 мм с каждой из сторон на технологический отступ( итого 6 мм)
     *
     * @param labelWight ширина этикетки, задается пользователем.передается из формы. минимум 6 максимум 304мм
     */
    public double getMaterialWidght(int labelWight) {
        int report = labelWight + 6; //определяем рапорт - добавляем к ширине этикетки 6мм отступа
        int rowCount = printWidth / (report); //определем максимальное количество рядов печати округляем до целого
        int materialWidght = report * rowCount + 20; // ширина материала = рапорт(ширина этикетки + 3мм на отступ с каждой стороны(6 мм))* число рядов печати + 20мм технологический отступ.

        return materialWidght;
    }
}