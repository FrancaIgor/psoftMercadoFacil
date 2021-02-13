package com.ufcg.psoft.util;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.Venda;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GenericQuickSort {


    public GenericQuickSort() {

    }

    public <T extends Comparable<T>> void quickSortPreco(List<Produto> arr, int a, int b) {
        if (a < b) {
            int i = a, j = b;
            Produto x = arr.get((i + j) / 2);

            do {
                while (arr.get(i).getPreco().compareTo(x.getPreco()) < 0) i++;
                while (x.getPreco().compareTo(arr.get(j).getPreco()) < 0) j--;

                if (i <= j) {
                    Produto tmp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, tmp);
                    i++;
                    j--;
                }

            } while (i <= j);
            quickSortPreco(arr, a, j);
            quickSortPreco(arr, i, b);
        }
    }

    public <T extends Comparable<T>> void quickSortCategoria(List<Produto> arr, int a, int b) {
        if (a < b) {
            int i = a, j = b;
            Produto x = arr.get((i + j) / 2);

            do {
                while (arr.get(i).getCategoria().getNome().compareTo(x.getCategoria().getNome()) < 0) i++;
                while (x.getCategoria().getNome().compareTo(arr.get(j).getCategoria().getNome()) < 0) j--;

                if (i <= j) {
                    Produto tmp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, tmp);
                    i++;
                    j--;
                }

            } while (i <= j);
            quickSortCategoria(arr, a, j);
            quickSortCategoria(arr, i, b);
        }
    }

    public void quickSortNome(List<Produto> arr, int a, int b) {
        if (a < b) {
            int i = a, j = b;
            Produto x = arr.get((i + j) / 2);

            do {
                while (arr.get(i).getNome().compareTo(x.getNome()) < 0) i++;
                while (x.getNome().compareTo(arr.get(j).getNome()) < 0) j--;

                if (i <= j) {
                    Produto tmp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, tmp);
                    i++;
                    j--;
                }

            } while (i <= j);
            quickSortNome(arr, a, j);
            quickSortNome(arr, i, b);
        }
    }

    public void quickSortFabricante(List<Produto> arr, int a, int b) {
        if (a < b) {
            int i = a, j = b;
            Produto x = arr.get((i + j) / 2);

            do {
                while (arr.get(i).getFabricante().compareTo(x.getFabricante()) < 0) i++;
                while (x.getFabricante().compareTo(arr.get(j).getFabricante()) < 0) j--;

                if (i <= j) {
                    Produto tmp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, tmp);
                    i++;
                    j--;
                }

            } while (i <= j);
            quickSortFabricante(arr, a, j);
            quickSortFabricante(arr, i, b);
        }
    }

    public <T extends Comparable<T>> void quickSortVenda(List<Venda> arr, int a, int b) {
        if (a < b) {
            int i = a, j = b;
            Venda x = arr.get((i + j) / 2);

            do {
                while (arr.get(i).getDataVenda().compareTo(x.getDataVenda()) < 0) i++;
                while (x.getDataVenda().compareTo(arr.get(j).getDataVenda()) < 0) j--;

                if (i <= j) {
                    Venda tmp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, tmp);
                    i++;
                    j--;
                }

            } while (i <= j);
            quickSortVenda(arr, a, j);
            quickSortVenda(arr, i, b);
        }
    }

    public <T extends Comparable<T>> void quickSortVendaValor(List<Venda> arr, int a, int b) {
        if (a < b) {
            int i = a, j = b;
            Venda x = arr.get((i + j) / 2);

            do {
                while (arr.get(i).getValor().compareTo(x.getValor()) < 0) i++;
                while (x.getValor().compareTo(arr.get(j).getValor()) < 0) j--;

                if (i <= j) {
                    Venda tmp = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, tmp);
                    i++;
                    j--;
                }

            } while (i <= j);
            quickSortVendaValor(arr, a, j);
            quickSortVendaValor(arr, i, b);
        }
    }
}
