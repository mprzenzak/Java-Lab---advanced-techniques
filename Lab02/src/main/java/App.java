import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class App {

    private JFrame frame;
    private JPanel panelFiles;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnSelect;
    private JFileChooser fileChooser;
    private ArrayList<WeakReference<File>> loadedFiles = new ArrayList<WeakReference<File>>();
    private final Mode mode = Mode.CSV;
    private final ArithmeticModel arithmeticModel = ArithmeticModel.AVERAGE;

    public App() {
        System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
        System.out.println("Free memory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 400));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panelFiles = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panelFiles);
        frame.add(scrollPane, BorderLayout.CENTER);
        boolean fileLoadedFromCache = false;

        btnSelect = new JButton("Select directory");
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File directory = fileChooser.getSelectedFile();
                    showFiles(directory, fileLoadedFromCache);
                }
            }
        });
        frame.add(btnSelect, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private void showFiles(File directory, boolean fileLoadedFromCache) {
        panelFiles.removeAll();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                JButton btnFolder = new JButton(file.getName());
                File finalFile = file;
                btnFolder.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showFiles(finalFile, false);
                    }
                });
                panelFiles.add(btnFolder);
            } else {
                //boolean fileLoadedFromCache = false;
                loadedFiles.removeIf(loadedFile -> loadedFile.get() == null);
                for (WeakReference<File> loadedFile : loadedFiles) {
                    if (loadedFile.get().equals(file)) {
                        file = loadedFile.get();
                        fileLoadedFromCache = true;
                        break;
                    }
                }

                JLabel labelFile = new JLabel(file.getName(), SwingConstants.CENTER);
                File finalFile1 = file;
                boolean finalFileLoadedFromCache = fileLoadedFromCache;
                labelFile.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        try {
                            showTable(finalFile1, finalFileLoadedFromCache);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                panelFiles.add(labelFile);
            }
            WeakReference<File> weakFile = new WeakReference<>(file);
            loadedFiles.add(weakFile);
        }
        frame.validate();
        frame.repaint();
    }

    private void showTable(File file, boolean fileLoadedFromCache) throws IOException {
        System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
        System.out.println("Free memory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
        ArrayList<Double> pressureValues = new ArrayList<Double>();
        ArrayList<Double> temperatureValues = new ArrayList<Double>();
        ArrayList<Double> humidityValues = new ArrayList<Double>();

        if (mode == Mode.CSV) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            String[] columns = line.split(";");
            model = new DefaultTableModel(columns, 0);


            while ((line = br.readLine()) != null) {
                String[] dataLine = line.split(";");
                model.addRow(dataLine);
                pressureValues.add(Double.parseDouble(dataLine[1].replace(",", ".")));
                temperatureValues.add(Double.parseDouble(dataLine[2].replace(",", ".")));
                humidityValues.add(Double.parseDouble(dataLine[3].replace(",", ".")));
            }
            br.close();
        }

        if (arithmeticModel == ArithmeticModel.AVERAGE) {
            model.addRow(new String[]{"srednia:", Arithmetic.average(pressureValues), Arithmetic.average(temperatureValues), Arithmetic.average(humidityValues)});
        }

        table = new JTable(model);
        JPanel summaryPanel = new JPanel();
        summaryPanel.add(new JScrollPane(table));
        if (fileLoadedFromCache) {
            summaryPanel.add(new JLabel("From memory"));
        } else {
            summaryPanel.add(new JLabel("Just loaded"));
        }
        JOptionPane.showMessageDialog(frame, summaryPanel, "Table", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        new App();
    }
}