package com.github.atamanroman.muellsammler.nbg;

import com.github.atamanroman.muellsammler.PickUp;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.function.Predicate.not;

class NbgGelberSackApi {

  private static Logger log = LoggerFactory.getLogger(NbgGelberSackApi.class);

  GelberSackPdf fetch() {
    return new GelberSackPdf(new byte[] {});
  }

  static class GelberSackPdf {
    private byte[] pdf;

    GelberSackPdf(byte[] pdf) {
      this.pdf = pdf;
    }

    public byte[] getBytes() {
      return pdf;
    }

    Set<PickUp> parse() {
      try {
        var pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setStartPage(3);
        pdfTextStripper.setEndPage(18);
        var target = new StringWriter();
        var document = PDDocument.load(pdf);
        pdfTextStripper.writeText(document, target);
        var lines = target.toString().lines()
          .filter(not(this::isJunk))
          .filter(not(this::isBroken));
        log.trace(
          "Raw text from Gelber Sack PDF (after cleanup)\n---\n{}\n---",
          lines.collect(Collectors.joining("\n"))
        );
      } catch (IOException e) {
        throw new NbgTrashException("Could not parse Gelber Sack PDF", e);
      }
      return Set.of();
    }

    private boolean isJunk(String line) {
      var cleanLine = line.trim();
      boolean isHeader = cleanLine.endsWith("Tournr.Hausnr.");
      boolean isFooter = cleanLine.contains("hofmann");
      boolean isBlank = cleanLine.isBlank();

      boolean junk = isHeader || isFooter || isBlank;
      if (junk) {
        log.trace("Drop junk line='{}'", line);
      }
      return junk;
    }

    private boolean isBroken(String line) {
      if (Character.isLowerCase(line.toCharArray()[0])) {
        log.info("Drop broken line='{}'", line);
        return true;
      }
      return false;
    }
  }

}
