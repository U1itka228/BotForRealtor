import model.Customer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private WorkWithCustomerDB workWithCustomerDB;

    public Bot(WorkWithCustomerDB workWithCustomerDB) {
        this.workWithCustomerDB = workWithCustomerDB;
    }

    private String name;
    private String phone;
    private String city;
    private String type;
    private LocalDateTime timeToContact;
    private String location;
    private String formOfPayment;
    private String heatingLevel;
    private Long realtorID;
    private String layout;
    private String shortDescription;

    //TODO Состояния ввода данных о клиенте (и не только)
    GetMessageState getMessageState = new GetMessageState();

    //TODO Кнопка для создания пользователя
    private final InlineKeyboardButton buttonForCreateCustomer = InlineKeyboardButton.builder()
            .text("➕ Создать клиента")
            .callbackData("create_customer")
            .build();

    //TODO Кнопка для клиентов под вопросом
    private final InlineKeyboardButton buttonForQuestionableCustomers = InlineKeyboardButton.builder()
            .text("❓ Клиенты на рассмотрении")
            .callbackData("questionable_customers")
            .build();

    //TODO Кнопка для архива клиентов
    private final InlineKeyboardButton buttonForArchiveCustomers = InlineKeyboardButton.builder()
            .text("\uD83D\uDCC1 Архив клиентов")
            .callbackData("archive_customer")
            .build();

    //TODO Кнопка для редактирования
    private final InlineKeyboardButton buttonForRedactCustomer = InlineKeyboardButton.builder()
            .text("✏\uFE0F Редактировать клиента")
            .callbackData("redact_customer")
            .build();

    //TODO Клавиатура для главного меню
    private final InlineKeyboardMarkup keyboardForMainMenu = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForCreateCustomer))
            .keyboardRow(List.of(buttonForRedactCustomer))
            .keyboardRow(List.of(buttonForQuestionableCustomers))
            .keyboardRow(List.of(buttonForArchiveCustomers))
            .build();

    //TODO Кнопка для измены имени
    private final InlineKeyboardButton buttonForChangeName = InlineKeyboardButton.builder()
            .text("\uD83D\uDCDD  Имя")
            .callbackData("change_name")
            .build();

    //TODO Кнопка для измены номера
    private final InlineKeyboardButton buttonForChangeNumber = InlineKeyboardButton.builder()
            .text("\uD83D\uDCDE  Номер телефона")
            .callbackData("change_number")
            .build();

    //TODO Кнопка для измены типа недвижимости
    private final InlineKeyboardButton buttonForChangeEstate = InlineKeyboardButton.builder()
            .text("\uD83C\uDFE0  Тип недвижимости")
            .callbackData("change_estate")
            .build();

    //TODO Кнопка для измены города
    private final InlineKeyboardButton buttonForChangeCity = InlineKeyboardButton.builder()
            .text("\uD83D\uDCCD  Город")
            .callbackData("change_city")
            .build();

    //TODO Кнопка для измены агента
    private final InlineKeyboardButton buttonForChangeRealtor = InlineKeyboardButton.builder()
            .text("\uD83D\uDC64  Передать агенту")
            .callbackData("change_realtor")
            .build();

    //TODO Кнопка для измены времени
    private final InlineKeyboardButton buttonForChangeTime = InlineKeyboardButton.builder()
            .text("⏳  Время контакта")
            .callbackData("change_time")
            .build();

    //TODO Клавиатура для редактирования
    private final InlineKeyboardMarkup keyboardForEdit = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForChangeName, buttonForChangeNumber))
            .keyboardRow(List.of(buttonForChangeEstate, buttonForChangeCity))
            .keyboardRow(List.of(buttonForChangeRealtor, buttonForChangeTime))
            .build();

    //TODO Тип: Апартаменты
    private final InlineKeyboardButton apartments = InlineKeyboardButton.builder()
            .text("\uD83C\uDFE2 Апартаменты")
            .callbackData("Апартаменты")
            .build();

    //TODO Тип: Квартира
    private final InlineKeyboardButton flat = InlineKeyboardButton.builder()
            .text("\uD83C\uDFE0 Квартира")
            .callbackData("Квартира")
            .build();

    //TODO Тип: Частный дом
    private final InlineKeyboardButton privateHouse = InlineKeyboardButton.builder()
            .text("\uD83C\uDFE1 Частный дом")
            .callbackData("Частный дом")
            .build();

    //TODO Тип: Коммерция
    private final InlineKeyboardButton commerce = InlineKeyboardButton.builder()
            .text("\uD83C\uDFEC Коммерция")
            .callbackData("Коммерция")
            .build();

    //TODO Тип: Земля
    private final InlineKeyboardButton ground = InlineKeyboardButton.builder()
            .text("\uD83C\uDF31 Земля")
            .callbackData("Земля")
            .build();

    //TODO Клавиатура для выбора типа недвижимости
    private final InlineKeyboardMarkup keyboardForChooseType = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(apartments, flat))
            .keyboardRow(List.of(privateHouse, commerce))
            .keyboardRow(List.of(ground))
            .build();

    //TODO Время: 1 час
    private final InlineKeyboardButton buttonFor1h = InlineKeyboardButton.builder()
            .text("⏱\uFE0F1 час")
            .callbackData("1_hour")
            .build();

    //TODO Время: 2 часа
    private final InlineKeyboardButton buttonFor2h = InlineKeyboardButton.builder()
            .text("⏱\uFE0F2 часа")
            .callbackData("2_hours")
            .build();

    //TODO Время: 8 часов
    private final InlineKeyboardButton buttonFor8h = InlineKeyboardButton.builder()
            .text("⏱\uFE0F8 часов")
            .callbackData("8_hours")
            .build();

    //TODO Время: 24 часа
    private final InlineKeyboardButton buttonFor24h = InlineKeyboardButton.builder()
            .text("⏱\uFE0F24 часа")
            .callbackData("24_hours")
            .build();

    //TODO Время: 2 дня
    private final InlineKeyboardButton buttonFor2Days = InlineKeyboardButton.builder()
            .text("⏱\uFE0F2 дня")
            .callbackData("2_days")
            .build();

    //TODO Время: свое
    private final InlineKeyboardButton buttonForChooseTimeForYourself = InlineKeyboardButton.builder()
            .text("✏\uFE0F Указать свое время")
            .callbackData("choose_yourself")
            .build();

    //TODO Клавиатура для выбора времени для связи
    private final InlineKeyboardMarkup keyboardForChooseTime = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonFor1h, buttonFor2h))
            .keyboardRow(List.of(buttonFor8h, buttonFor24h))
            .keyboardRow(List.of(buttonFor2Days))
            .keyboardRow(List.of(buttonForChooseTimeForYourself))
            .build();

    private final InlineKeyboardMarkup keyboardForSimpleChooseTime = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonFor1h, buttonFor2h))
            .keyboardRow(List.of(buttonFor8h, buttonFor24h))
            .keyboardRow(List.of(buttonFor2Days))
            .build();

    //TODO кнопка для полного создания клиента
    private final InlineKeyboardButton buttonForFullVersionCreate = InlineKeyboardButton.builder()
            .text("\uD83C\uDF1F Расширенная версия")
            .callbackData("full_version_create")
            .build();

    //TODO кнопка для краткого создания клиента
    private final InlineKeyboardButton buttonForShortVersionCreate = InlineKeyboardButton.builder()
            .text("⚡ Быстрая версия")
            .callbackData("short_version_create")
            .build();

    //TODO Клавиатура для выбора версии создания клиента
    private final InlineKeyboardMarkup keyboardForVersionsCreate = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForFullVersionCreate, buttonForShortVersionCreate))
            .build();

    //TODO Локация: береговая линия
    public static InlineKeyboardButton buttonForCoastline = InlineKeyboardButton.builder()
            .text("\uD83C\uDF0A Береговая линия")
            .callbackData("Береговая линия")
            .build();

    //TODO Локация: Сочи
    public static InlineKeyboardButton buttonForSochi = InlineKeyboardButton.builder()
            .text("\uD83C\uDFD6 Сочи")
            .callbackData("Сочи")
            .build();

    //TODO Локация: Адлер
    public static InlineKeyboardButton buttonForAdler = InlineKeyboardButton.builder()
            .text("\uD83C\uDFD9 Адлер")
            .callbackData("Адлер")
            .build();

    //TODO Локация: Красная поляна
    public static InlineKeyboardButton buttonForKrasnayaPolyana = InlineKeyboardButton.builder()
            .text("\uD83C\uDFD4 Красная поляна")
            .callbackData("Красная поляна")
            .build();

    //TODO Клавиатура для локации
    public static InlineKeyboardMarkup keyboardForLocation = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForCoastline, buttonForSochi))
            .keyboardRow(List.of(buttonForAdler, buttonForKrasnayaPolyana))
            .build();

    public static InlineKeyboardButton studio = InlineKeyboardButton.builder()
            .text("\uD83C\uDFA8 Студия")
            .callbackData("Студия")
            .build();

    public static InlineKeyboardButton euro2 = InlineKeyboardButton.builder()
            .text("\uD83C\uDFE2 Евро-2")
            .callbackData("Евро-2")
            .build();

    public static InlineKeyboardButton euro3 = InlineKeyboardButton.builder()
            .text("\uD83C\uDFE2 Евро-3")
            .callbackData("Евро-3")
            .build();

    public static InlineKeyboardButton buttonForChooseLayoutForYourself = InlineKeyboardButton.builder()
            .text("\uD83D\uDD8A Ввести планировку вручную")
            .callbackData("choose_layout")
            .build();

    public static InlineKeyboardMarkup keyboardForLayout = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(studio, euro2))
            .keyboardRow(List.of(euro3, buttonForChooseLayoutForYourself))
            .build();

    public static InlineKeyboardButton fullCash = InlineKeyboardButton.builder()
            .text("\uD83D\uDCB5 Полный наличный")
            .callbackData("Полный наличный")
            .build();

    public static InlineKeyboardButton installmentPlan = InlineKeyboardButton.builder()
            .text("\uD83D\uDCC5 Рассрочка")
            .callbackData("Рассрочка")
            .build();

    public static InlineKeyboardButton familyMortgage = InlineKeyboardButton.builder()
            .text("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Семейная ипотека")
            .callbackData("Семейная ипотека")
            .build();

    public static InlineKeyboardButton withoutADownPayment = InlineKeyboardButton.builder()
            .text("\uD83D\uDEAB Без первоначального взноса")
            .callbackData("Без первоначального взноса")
            .build();

    public static InlineKeyboardMarkup keyboardForPayment = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(fullCash, installmentPlan))
            .keyboardRow(List.of(familyMortgage, withoutADownPayment))
            .build();

    public static InlineKeyboardButton hot = InlineKeyboardButton.builder()
            .text("\uD83D\uDD25 Горячий")
            .callbackData("Горячий")
            .build();

    public static InlineKeyboardButton warm = InlineKeyboardButton.builder()
            .text("\uD83C\uDF24 Теплый")
            .callbackData("Теплый")
            .build();

    public static InlineKeyboardButton cold = InlineKeyboardButton.builder()
            .text("❄ Холодный")
            .callbackData("Холодный")
            .build();

    public static InlineKeyboardMarkup keyboardForHeatingLevel = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(hot, warm))
            .keyboardRow(List.of(cold))
            .build();

    //TODO кнопка для отправки клиента в архив
    public static InlineKeyboardButton buttonForSendCustomerInArchive = InlineKeyboardButton.builder()
            .text("Отправить в архив")
            .callbackData("Отправить в архив")
            .build();
    //TODO клава для кнопки выше
    public static  InlineKeyboardMarkup markupForSendCustomerInArchive = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForSendCustomerInArchive))
            .build();
    //TODO кнопка для отправки из архива в клиенты под вопросом
    public static InlineKeyboardButton buttonForSendArchiveCustomerInCustomer = InlineKeyboardButton.builder()
            .text("Отправить в клиенты под вопросом")
            .callbackData("Отправить в клиенты под вопросом")
            .build();
    //TODO клава для кнопки выше
    public static  InlineKeyboardMarkup markupForSendArchiveCustomerInCustomer = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(buttonForSendArchiveCustomerInCustomer))
            .build();




    //TODO Чтобы не писать каждый раз блок try/catch
    public void tryCatch(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void tryCatch(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean handleTimeSelection(EditMessageText editMessageText, String callbackData) {
        if (!getMessageState.isWaitingTime()) return false;

        if (callbackData.equals(buttonFor1h.getCallbackData())) {
            timeToContact = LocalDateTime.now().plusHours(1);
        } else if (callbackData.equals(buttonFor2h.getCallbackData())) {
            timeToContact = LocalDateTime.now().plusHours(2);
        } else if (callbackData.equals(buttonFor8h.getCallbackData())) {
            timeToContact = LocalDateTime.now().plusHours(8);
        } else if (callbackData.equals(buttonFor24h.getCallbackData())) {
            timeToContact = LocalDateTime.now().plusHours(24);
        } else if (callbackData.equals(buttonFor2Days.getCallbackData())) {
            timeToContact = LocalDateTime.now().plusHours(48);
        } else if (callbackData.equals(buttonForChooseTimeForYourself.getCallbackData())) {
            getMessageState.setWaitingTime(false);
            getMessageState.setTimeYourself(true);
            editMessageText.setText("⏳ Введите количество часов:");
            editMessageText.setReplyMarkup(null);
            tryCatch(editMessageText);
            return true;
        } else {
            return false;
        }

        getMessageState.setWaitingTime(false);

        if (getMessageState.isShortVersion()) {
            getMessageState.setCreateCustomerInDB(true);
            Customer customer = new Customer(
                    name, phone, city,
                    null,
                    timeToContact,
                    realtorID,
                    null, null, null,
                    shortDescription
            );
            editMessageText.setText("Клиент создан ✅\n\n" + customer);
            editMessageText.setReplyMarkup(keyboardForMainMenu);
            tryCatch(editMessageText);
        } else {
            getMessageState.setHeatingLevel(true);
            editMessageText.setText("Выберите уровень клиента:");
            editMessageText.setReplyMarkup(keyboardForHeatingLevel);
            tryCatch(editMessageText);
        }

        return true;
    }

    public void shortCreateVersion(EditMessageText editMessageText, String callbackData) {
        if (callbackData.equals(buttonForShortVersionCreate.getCallbackData())) {
            type = null;
            getMessageState.setChoiceVersion(false);
            getMessageState.setShortDescription(true);
            editMessageText.setText("📝 Введите краткое описание клиента:");
            editMessageText.setReplyMarkup(null);
            tryCatch(editMessageText);
        }
    }

    public void fullCreateVersion(EditMessageText editMessageText, String callbackData) {
        if (callbackData.equals(buttonForFullVersionCreate.getCallbackData())) {
            editMessageText.setText("\uD83D\uDCCD Локация объекта\nВыберите локацию:");
            editMessageText.setReplyMarkup(keyboardForLocation);
            getMessageState.setLocation(true);
            tryCatch(editMessageText);
        } else if (getMessageState.isCreateCustomer() && getMessageState.isLocation()) {
            location = callbackData;
            editMessageText.setText("\uD83C\uDFE1 Тип недвижимости\n" +
                    "Пожалуйста, выберите подходящий вариант:");
            editMessageText.setReplyMarkup(keyboardForChooseType);
            getMessageState.setLocation(false);
            getMessageState.setWaitingType(true);
            tryCatch(editMessageText);
        } else if (getMessageState.isCreateCustomer() && getMessageState.isWaitingType()) {
            type = callbackData;
            if (callbackData.equals(flat.getCallbackData())) {
                editMessageText.setText("\uD83D\uDCD0 Планировка квартиры\n" +
                        "Выберите подходящий вариант или введите самостоятельно:\n");
                editMessageText.setReplyMarkup(keyboardForLayout);
                getMessageState.setLayout(true);
            } else {
                editMessageText.setText("Выберите форму оплаты:");
                editMessageText.setReplyMarkup(keyboardForPayment);
                getMessageState.setFormOfPayment(true);
            }
            getMessageState.setWaitingType(false);
            tryCatch(editMessageText);
        } else if (getMessageState.isCreateCustomer() && getMessageState.isLayout()) {
            layout = callbackData;
            type += ": " + layout;
            if (callbackData.equals(buttonForChooseLayoutForYourself.getCallbackData())) {
                getMessageState.setLayout(false);
                getMessageState.setLayoutYourself(true);
                editMessageText.setText("Введите планировку самостоятельно:");
                tryCatch(editMessageText);
                return;
            }
            getMessageState.setLayout(false);
            getMessageState.setFormOfPayment(true);
            editMessageText.setText("Выберите форму оплаты:");
            editMessageText.setReplyMarkup(keyboardForPayment);
            tryCatch(editMessageText);
        } else if (getMessageState.isCreateCustomer() && getMessageState.isFormOfPayment()) {
            formOfPayment = callbackData;
            editMessageText.setText("⏱ Время для связи\n" +
                    "Выберите, через сколько часов нужно связаться с клиентом:");
            editMessageText.setReplyMarkup(keyboardForChooseTime);
            getMessageState.setFormOfPayment(false);
            getMessageState.setWaitingTime(true);
            tryCatch(editMessageText);
        } else if (getMessageState.isCreateCustomer()
                && getMessageState.isWaitingTime() &&
                callbackData.equals(buttonForChooseTimeForYourself.getCallbackData())) {
            editMessageText.setText("Введите время в часах");
            getMessageState.setTimeYourself(true);
        } else if (getMessageState.isCreateCustomer() && getMessageState.isHeatingLevel()) {
            heatingLevel = callbackData;
            getMessageState.setHeatingLevel(false);
            getMessageState.setCreateCustomerInDB(true);
            Customer customer = new Customer(name, phone, city, type, timeToContact, realtorID, location, formOfPayment, heatingLevel, shortDescription);
            editMessageText.setText("Клиент создан ✅\n\n" + customer);
            editMessageText.setReplyMarkup(keyboardForMainMenu);
            tryCatch(editMessageText);
        }
    }

    public void forWorkWithText(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String textMessage = update.getMessage().getText();
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text("")
                    .build();
            System.out.println("Текст от пользователя " + chatId + ": " + textMessage);

            if (textMessage.equals("/start")) {
                getMessageState.setTimeYourself(false);
                sendMessage.setText("\uD83C\uDFE0 Вас приветствует чат-бот для агентов по недвижимости!\n\nБот предоставляет возможности:\n        • по созданию/редактированию клиентов в базе" +
                        "\n        • по автоматическому созданию напоминаний о звонках\n        • по передаче клиентов другому агенту\n        • по управлению базой клиентов" +
                        "\n______________________________________\nВыберите действие из меню ниже:");
                sendMessage.setReplyMarkup(keyboardForMainMenu);
            } else if (getMessageState.isCreateCustomer() && getMessageState.isWaitingName()) {
                name = textMessage;
                getMessageState.setWaitingName(false);
                getMessageState.setWaitingPhone(true);
                sendMessage.setText("\uD83D\uDCF1 Телефон клиента\n" +
                        "Введите номер телефона в формате 89181234567 или +79181234567:\n");
            } else if (getMessageState.isCreateCustomer() && getMessageState.isWaitingPhone()) {
                try {
                    phone = textMessage;
                    if (textMessage.startsWith("+7")) {
                        phone = "8" + phone.substring(1);
                    }
                    if (!phone.startsWith("8") && !phone.startsWith("+7")) {
                        sendMessage.setText("Номер должен начинаться с 8 или +7.\nПример: 89181234567, +79181234567\nПожалуйста, введите номер еще раз:");
                        getMessageState.setWaitingPhone(true);
                    } else {
                        getMessageState.setWaitingPhone(false);
                        getMessageState.setWaitingCity(true);
                        sendMessage.setText("\uD83D\uDCCD Город:\n\nВведите город и часовой пояс:");
                    }
                } catch (Exception e) {
                    sendMessage.setText("Некорректный номер телефона. Пожалуйста, введите только цифры:\nПример: 89181234567");
                    getMessageState.setWaitingPhone(true);
                }
            } else if (getMessageState.isCreateCustomer() && getMessageState.isWaitingCity()) {
                city = textMessage;
                getMessageState.setWaitingCity(false);
                getMessageState.setChoiceVersion(true);
                sendMessage.setText("\uD83D\uDCDD Версия создания клиента\n" +
                        "Выберите способ добавления клиента:");
                sendMessage.setReplyMarkup(keyboardForVersionsCreate);
            } else if (getMessageState.isCreateCustomer() && getMessageState.isLayoutYourself()) {
                layout = textMessage;
                type += ": " + layout;
                getMessageState.setLayoutYourself(false);
                getMessageState.setFormOfPayment(true);
                sendMessage.setText("\uD83D\uDCB0 Форма оплаты\n" +
                        "Пожалуйста, выберите подходящий вариант:\n");
                sendMessage.setReplyMarkup(keyboardForPayment);
            } else if (getMessageState.isCreateCustomer() && getMessageState.isTimeYourself()) {
                try {
                    int hours = Integer.parseInt(textMessage);
                    timeToContact = LocalDateTime.now().plusHours(hours);
                    getMessageState.setTimeYourself(false);
                    if (shortDescription != null) {
                        getMessageState.setCreateCustomerInDB(true);
                        Customer customer = new Customer(
                                name, phone, city, null, timeToContact, realtorID, null, null, null, shortDescription
                        );
                        sendMessage.setText("Клиент создан ✅\n\n" + customer);
                        sendMessage.setReplyMarkup(keyboardForMainMenu);
                    } else {
                        getMessageState.setHeatingLevel(true);
                        sendMessage.setText("Выберите уровень клиента:");
                        sendMessage.setReplyMarkup(keyboardForHeatingLevel);
                    }
                } catch (NumberFormatException e) {
                    sendMessage.setText("Введите корректное число часов");
                }
            } else if (getMessageState.isCreateCustomer() && getMessageState.isShortDescription()) {
                shortDescription = textMessage;
                getMessageState.setShortDescription(false);
                getMessageState.setWaitingTime(true);
                sendMessage.setText("Выберите время для напоминания:");
                sendMessage.setReplyMarkup(keyboardForChooseTime);
            }
            //TODO по номеру поиск клиента
             else if (getMessageState.isNeedSelectByPhoneNumberForCustomer()) {
                 String phoneNumber = textMessage;
                 getMessageState.setCustomerForSendInArchive(workWithCustomerDB.getCustomerByPhoneNumber(phoneNumber)) ;
                sendMessage.setText(getMessageState.getCustomerForSendInArchive().toString());
                sendMessage.setReplyMarkup(markupForSendCustomerInArchive);

                getMessageState.setNeedSelectByPhoneNumberForCustomer(false);
            }
            //TODO по номеру поиск архивного клиента
             else if (getMessageState.isNeedSelectByPhoneNumberForArhiveCustomer()) {
                String archivePhoneNumber = textMessage;
                getMessageState.setArchiveCustomerForSendInQuestionableCustomer(workWithCustomerDB.getArchiveCustomerByPhoneNumber(archivePhoneNumber)) ;
                sendMessage.setText(getMessageState.getArchiveCustomerForSendInQuestionableCustomer().toString());
                sendMessage.setReplyMarkup(markupForSendArchiveCustomerInCustomer);

                getMessageState.setNeedSelectByPhoneNumberForArhiveCustomer(false);
            }


            tryCatch(sendMessage);
        }
    }

    public void forWorkWithButtons(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            realtorID = chatId;

            if (callbackData.equals(buttonForShortVersionCreate.getCallbackData())) {
                getMessageState.setShortVersion(true);
                getMessageState.setFullVersion(false);
            }

            if (callbackData.equals(buttonForFullVersionCreate.getCallbackData())) {
                getMessageState.setFullVersion(true);
                getMessageState.setShortVersion(false);
            }

            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            EditMessageText editMessageText = EditMessageText.builder()
                    .text("")
                    .chatId(chatId)
                    .messageId(messageId)
                    .build();

            //TODO КНОПКА клиенты "под вопросом" отправка документа и т.д.
            if (callbackData.equals(buttonForQuestionableCustomers.getCallbackData())){
                String path = workWithCustomerDB.createFileListAndReturnPath(1);
                SendDocument sendDocument = new SendDocument().builder()
                        .document(new InputFile(new File(path)))
                        .chatId(chatId)
                        .build();
                SendMessage sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text("")
                        .build();
                sendMessage.setText("Введите номер телефона клиента для отправки в архив: ");
                try {
                    execute(sendDocument);
                    execute(sendMessage);
                }catch (Exception e){
                    e.getMessage();
                }
                getMessageState.setNeedSelectByPhoneNumberForCustomer(true);
            }
            //TODO КНОПКА Архив
            else if (callbackData.equals(buttonForArchiveCustomers.getCallbackData())){
                String path = workWithCustomerDB.createFileListAndReturnPath(2);
                SendDocument sendDocument = new SendDocument().builder()
                        .document(new InputFile(new File(path)))
                        .chatId(chatId)
                        .build();
                SendMessage sendMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text("")
                        .build();
                sendMessage.setText("Введите номер телефона клиента для отправки на рассмотрение: ");
                try {
                    execute(sendDocument);
                    execute(sendMessage);
                }catch (Exception e){
                    e.getMessage();
                }
                getMessageState.setNeedSelectByPhoneNumberForArhiveCustomer(true);
            }
            else if(callbackData.equals(buttonForSendCustomerInArchive.getCallbackData())){
                workWithCustomerDB.createArchiveCustomer(getMessageState.getCustomerForSendInArchive());
                editMessageText.setText("Клиент успешно отправлен в архив!");
                getMessageState.setCustomerForSendInArchive(null);
                try {
                    execute(editMessageText);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            } else if (callbackData.equals(buttonForSendArchiveCustomerInCustomer.getCallbackData())) {
                editMessageText.setText("Выберите время для связи с клиентом:");
                editMessageText.setReplyMarkup(keyboardForSimpleChooseTime);handleTimeSelection(editMessageText, callbackData);
                getMessageState.setNeedTimeForTransferedCustomer(true);
                try {
                    execute(editMessageText);
                }catch (Exception e){
                    System.out.println("Ошибка: "+ e.getMessage());
                }
            } else if (getMessageState.isNeedTimeForTransferedCustomer()) {
                LocalDateTime time = null;
                if (callbackData.equals(buttonFor1h.getCallbackData())) {
                    time = LocalDateTime.now().plusHours(1);
                } else if (callbackData.equals(buttonFor2h.getCallbackData())) {
                    time = LocalDateTime.now().plusHours(2);
                } else if (callbackData.equals(buttonFor8h.getCallbackData())) {
                    time = LocalDateTime.now().plusHours(8);
                } else if (callbackData.equals(buttonFor24h.getCallbackData())) {
                    time = LocalDateTime.now().plusHours(24);
                } else if (callbackData.equals(buttonFor2Days.getCallbackData())) {
                    time = LocalDateTime.now().plusHours(48);
                }

                workWithCustomerDB.createQuestionableCustomer(getMessageState.getArchiveCustomerForSendInQuestionableCustomer(), time);
                editMessageText.setText("Клиент успешно отправлен на рассмотрение!");
                try {
                    execute(editMessageText);
                }catch (Exception e){
                    System.out.println("Ошибка: "+ e.getMessage());
                }
                getMessageState.setNeedTimeForTransferedCustomer(false);

            }

            try {
                //TODO Редактирование клиента
                if (callbackData.equals(buttonForRedactCustomer.getCallbackData())) {
                    editMessageText.setText("✏\uFE0F Редактирование клиента\n\nВыберите что хотите изменить:");
                    editMessageText.setReplyMarkup(keyboardForEdit);
                    tryCatch(editMessageText);
                } else if (callbackData.equals("create_customer")) {
                    //TODO Начало создания пользователя
                    getMessageState.setCreateCustomer(true);
                    getMessageState.setWaitingName(true);
                    editMessageText.setText("✨ СОЗДАНИЕ КЛИЕНTA ✨\n\nСейчас зададим вам несколько вопросов, чтобы всё было точно." +
                            "\n\uD83D\uDC47 Введите имя клиента:");
                    tryCatch(editMessageText);
                } else if (getMessageState.isCreateCustomer()) {
                    if (handleTimeSelection(editMessageText, callbackData)) return;
                    if (getMessageState.getFullVersion()) fullCreateVersion(editMessageText, callbackData);
                    if (getMessageState.isShortVersion()) shortCreateVersion(editMessageText, callbackData);
                } else if (getMessageState.isCreateCustomer() &&
                        (callbackData.equals(apartments.getCallbackData()) ||
                                callbackData.equals(flat.getCallbackData()) ||
                                callbackData.equals(privateHouse.getCallbackData()) ||
                                callbackData.equals(commerce.getCallbackData()) ||
                                callbackData.equals(ground.getCallbackData()))) {
                    type = callbackData;
                    getMessageState.setWaitingType(false);
                    getMessageState.setWaitingTime(true);
                }
            //TODO __________________________________________________________________________________
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    @Override
    public String getBotUsername() {
        return "@B0tForMyself_bot";
    }

    @Override
    public String getBotToken() {
        return "7692451763:AAHYrZ4LgZfDICuYgs-dcaNbhYu4hJuOSnI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        forWorkWithText(update);
        forWorkWithButtons(update);
        System.out.println("callback: " + update.getCallbackQuery().getData() + "\ntimeToContact: " + timeToContact);
        if (getMessageState.isCreateCustomerInDB()) {
            workWithCustomerDB.createCustomer(name, phone, city, type, timeToContact, realtorID, location, formOfPayment, heatingLevel, shortDescription);
            name = null;
            phone = null;
            city = null;
            type = null;
            timeToContact = null;
            realtorID = null;
            location = null;
            formOfPayment = null;
            heatingLevel = null;
            shortDescription = null;
            getMessageState.setCreateCustomerInDB(false);
        }
    }
}
