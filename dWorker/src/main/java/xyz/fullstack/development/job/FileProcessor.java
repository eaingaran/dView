package xyz.fullstack.development.job;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.fullstack.development.dao.FileDetailRepository;
import xyz.fullstack.development.dao.SourceRepository;
import xyz.fullstack.development.dao.UserRepository;
import xyz.fullstack.development.domain.FileDetail;
import xyz.fullstack.development.domain.Source;
import xyz.fullstack.development.domain.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;

@Component
public class FileProcessor {

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileDetailRepository fileDetailRepository;

    @Scheduled(fixedRate = 300000)
    public void processSource() {
        System.out.println("Starting Processing...");
        Iterable<Source> sources = sourceRepository.findAll();

        sources.forEach(source -> processFile(source));
        System.out.println("Starting Finished...");
    }

    private void processFile(Source source) {
        File folder = new File(source.getPath());
        File[] files = folder.listFiles();

        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        if ("user".equalsIgnoreCase(source.getName())) {
                            parseUserFile(file, source);
                        }
                    }
                }
            } else {
                System.out.println("Path of file : " + folder.getAbsolutePath() + "  " + folder.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseUserFile(File file, Source source) throws Exception {
        FileDetail fileDetail = new FileDetail();
        fileDetail.setFileName(file.getName());
        fileDetail.setSource(source.getName());
        fileDetail.setDateProcessed(LocalDate.now().toString());
        BeanListProcessor<User> rowProcessor = new BeanListProcessor<User>(User.class);

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.getFormat().setDelimiter(',');
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(new BufferedReader(new FileReader(file)));

        List<User> beans = null;

        try {
            // The BeanListProcessor provides a list of objects extracted from the input.
            beans = rowProcessor.getBeans();
        } catch (Exception e) {
            e.printStackTrace();
            fileDetail.setError(e.getMessage());
            fileDetail.setProcessSuccessful("false");
            FileUtils.moveFile(file, new File(source.getPath() + "/failed/" + file.getName()));
        }
        if (fileDetail.getProcessSuccessful() == null) {
            userRepository.save(beans);
            fileDetail.setProcessSuccessful("true");
            FileUtils.moveFile(file, new File(source.getPath() + "/processed/" + file.getName()));
        }

        fileDetailRepository.save(fileDetail);
    }
}
