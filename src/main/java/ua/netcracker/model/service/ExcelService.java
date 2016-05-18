package ua.netcracker.model.service;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Владимир on 17.05.2016.
 */
public interface ExcelService {
    byte[] toXLSX(Collection<Collection<String>> report, String reportName) throws IOException;
}
