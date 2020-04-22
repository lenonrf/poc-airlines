package br.com.bexs.airlines.api.service;

import br.com.bexs.airlines.api.domain.entity.Route;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Service
public class FileReaderRouteService {

    public List<Route> readRoutesFromFile(MultipartFile file) throws Exception {

        List<Route> routeList = new ArrayList<>();
        InputStreamReader streamedFile = new InputStreamReader(file.getInputStream());

        try (Reader reader = new BufferedReader(streamedFile)) {

            CsvToBean<Route> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Route.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<Route> routeIterator = csvToBean.iterator();

            while (routeIterator.hasNext()) {
                routeList.add(routeIterator.next());
            }

        }catch (IOException e){
            throw new Exception("Arquivo não encontrado.");
        }

        return routeList;
    }
}
