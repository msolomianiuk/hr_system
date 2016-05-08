package ua.netcracker.model.service;

import com.lowagie.text.Document;
import ua.netcracker.model.entity.Candidate;

public interface GeneratePDFService {
    Document generatePDF(Candidate candidate);
    byte[] convertToBytes();
}
