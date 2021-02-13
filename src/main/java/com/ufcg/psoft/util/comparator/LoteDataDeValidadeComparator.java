package com.ufcg.psoft.util.comparator;

import com.ufcg.psoft.model.Lote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class LoteDataDeValidadeComparator implements Comparator<Lote> {
    @Override
    public int compare(Lote lote1, Lote lote2) {
        int result = 0;
        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(lote1.getDataDeValidade());
            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(lote2.getDataDeValidade());
            result = date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
