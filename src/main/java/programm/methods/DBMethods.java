package programm.methods;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import lombok.Getter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.Transactional;
import programm.controllers.MainController;
import programm.models.CPUTableModel;
import programm.models.MainTableModel;
import programm.models.RepairTableModel;

import java.util.List;

public class DBMethods {
    private static SimpleDriverDataSource dataSource;
    private static JdbcTemplate jdbcTemplate;

    @Getter
    private static ObservableList<MainTableModel> mainTableList = FXCollections.observableArrayList();
    @Getter
    private static ObservableList<RepairTableModel> repairTableList = FXCollections.observableArrayList();
    @Getter
    private static ObservableList<CPUTableModel> cpuCatalogList = FXCollections.observableArrayList();
    @Getter
    private static ObservableList<String> cpuList = FXCollections.observableArrayList();
    @Getter
    private static ObservableList<String> podrazdelList = FXCollections.observableArrayList();
    @Getter
    private static ObservableList<String> positionList = FXCollections.observableArrayList();
    @Getter
    private static ObservableList<Button> buttonsLinkList = FXCollections.observableArrayList();

    private static JdbcTemplate jdbcTemplate() {
        if (dataSource == null) {
            dataSource = new SimpleDriverDataSource();
            dataSource.setDriver(new oracle.jdbc.driver.OracleDriver());
            dataSource.setUrl("jdbc:oracle:thin:************************");
            dataSource.setUsername("***");
            dataSource.setPassword("***");
        }
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        try {   // проверка соединения с БД
            jdbcTemplate.queryForObject("SELECT 1 from DUAL", int.class);
        } catch (DataAccessException e) {
            MainController.showConnectionError();
        }
        return jdbcTemplate;
    }

    public static void fillMainTableList() {
        String sql = "select ID, ROW_NUMBER() OVER (ORDER BY PODRAZDEL) as counter, PODRAZDEL, CABINET_NUM, \"USER\", " +
                "NETWORK_NAME, POSITION, OS, PC_NUM, CPU, RAM, HDD, MONITOR, MONITOR_NUM, PRINT_DEVICE, PRINT_DEVICE_TYPE, " +
                "PRINT_DEVICE_NUM, (select \"START\" as repair from REPAIR_INFO where ID_W = WORKPLACE.ID order by ID " +
                "desc fetch first row only) as repair from WORKPLACE order by PODRAZDEL";
        List<MainTableModel> list = jdbcTemplate().query(sql, new BeanPropertyRowMapper<>(MainTableModel.class));
        mainTableList.addAll(list);
    }

    public static void addEmptyWorkplace(String podrazdel) {
        String sql = "insert into WORKPLACE (PODRAZDEL) values (?)";
        jdbcTemplate().update(sql, podrazdel);
    }

    public static void addEmptyCPU() {
        String sql = "insert into CPU_CATALOG (CPU) values (null)";
        jdbcTemplate().update(sql);
    }

    public static void saveCPU(CPUTableModel cpu) {
        String sql = "update CPU_CATALOG set CPU = ? where ID = ?";
        jdbcTemplate().update(sql, cpu.getCpu(), cpu.getId());
    }

    public static void removeCPU(int id) {
        String sql = "delete from CPU_CATALOG where ID = ?";
        jdbcTemplate().update(sql, id);
    }

    public static void updateWorkplace(MainTableModel workplace) {
        String sql = "update WORKPLACE set PODRAZDEL = ?, CABINET_NUM = ?, \"USER\" = ?, NETWORK_NAME = ?, POSITION = ?, " +
                "OS = ?, PC_NUM = ?, CPU = ?, RAM = ?, HDD = ?, MONITOR = ?, MONITOR_NUM = ?, PRINT_DEVICE = ?, " +
                "PRINT_DEVICE_TYPE = ?, PRINT_DEVICE_NUM = ? where ID = ?";
        jdbcTemplate().update(sql,
                workplace.getPodrazdel(),
                workplace.getCabinetNum(),
                workplace.getUser(),
                workplace.getNetworkName(),
                workplace.getPosition(),
                workplace.getOs(),
                workplace.getPcNum(),
                workplace.getCpu(),
                workplace.getRam(),
                workplace.getHdd(),
                workplace.getMonitor(),
                workplace.getMonitorNum(),
                workplace.getPrintDevice(),
                workplace.getPrintDeviceType(),
                workplace.getPrintDeviceNum(),
                workplace.getId());
    }

    @Transactional  // чтобы если первый sql кинул ошибку, то не выполнялся второй
    public void removeWorkplace(int rowId) {
        String sql1 = "delete from REPAIR_INFO where ID_W = ?";
        String sql2 = "delete from WORKPLACE where ID = ?";
        jdbcTemplate().update(sql1, rowId);
        jdbcTemplate().update(sql2, rowId);
    }

    public static void fillRepairTableList(int rowId) {
        String sql = "select * from REPAIR_INFO where ID_W = ? order by ID desc";
        List<RepairTableModel> list = jdbcTemplate().query(sql, new BeanPropertyRowMapper<>(RepairTableModel.class), rowId);
        repairTableList.addAll(list);
    }

    public static void updateRepair(RepairTableModel repair) {
        String sql = "update REPAIR_INFO set END = ?, OS = ?, MOTHERBOARD = ?, CPU = ?, RAM = ?, HDD = ?, " +
                "MONITOR = ?, BP = ?, NET_CARD = ?, PRINT_DEVICE = ? where ID = ?";
        jdbcTemplate().update(sql,
                repair.getEnd(),
                repair.getOs(),
                repair.getMotherboard(),
                repair.getCpu(),
                repair.getRam(),
                repair.getHdd(),
                repair.getMonitor(),
                repair.getBp(),
                repair.getNetCard(),
                repair.getPrintDevice(),
                repair.getId());
    }

    public static void addEmptyRepair(int rowId) {
        String sql = "insert into REPAIR_INFO (ID_W, \"START\") values (?,?)";
        jdbcTemplate().update(sql, rowId, SupportMethods.getTodayDate());
    }

    public static void removeRepairById(long rowId) {
        String sql = "delete from REPAIR_INFO where ID = ?";
        jdbcTemplate().update(sql, rowId);
    }

    public static void fillPodrazdelAndButtonsList() {
        String sql = "select * from PODRAZDEL_CATALOG order by PODRAZDEL";
        List<String> list = jdbcTemplate().queryForList(sql, String.class);
        getPodrazdelList().addAll(list);
        list.forEach(e -> buttonsLinkList.add(new Button(e)));
    }

    public static void fillCPUList() {
        String sql = "select CPU from CPU_CATALOG order by ID";
        List<String> list = jdbcTemplate().queryForList(sql, String.class);
        cpuList.addAll(list);
    }

    public static void fillCPUCatalogList() {
        String sql = "select ID, ROW_NUMBER() OVER (ORDER BY ID) as rating, cpu from CPU_CATALOG";
        List<CPUTableModel> list = jdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CPUTableModel.class));
        cpuCatalogList.addAll(list);
    }

    public static void fillPositionList() {
        String sql = "select POSITION from POSITION_CATALOG order by POSITION";
        List<String> list = jdbcTemplate().queryForList(sql, String.class);
        positionList.addAll(list);
    }

    @Transactional
    public void moveCPU(int cpuId, String currentCPU, int nearCPUId, String nearCPU) {
        String sql1 = "update CPU_CATALOG set CPU = ? where ID = ?";
        String sql2 = "update CPU_CATALOG set CPU = ? where ID = ?";
        jdbcTemplate().update(sql1, currentCPU, nearCPUId);
        jdbcTemplate().update(sql2, nearCPU, cpuId);
    }
}
