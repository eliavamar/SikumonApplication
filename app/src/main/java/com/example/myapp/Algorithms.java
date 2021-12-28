package com.example.myapp;

public class Algorithms {
    private static int min3(int a,int b, int c){
        return Math.min(Math.min(a,b),c);
    }
    public static int editDistance(String s1, String s2){
        int editMatrix[][]=new int[s1.length()+1][s2.length()+1];
        for (int i = 0; i < s1.length()+1; i++) {
            editMatrix[i][0]=i;
        }
        for (int i = 1; i < s2.length()+1; i++) {
            editMatrix[0][i]=i;
        }
        for (int i = 1; i < s1.length()+1 ; i++) {
            for (int j = 1; j < s2.length()+1 ; j++) {
                if(s1.charAt(i-1)==s2.charAt(j-1)) editMatrix[i][j]=editMatrix[i-1][j-1];
                else editMatrix[i][j]=1+min3(editMatrix[i][j-1],editMatrix[i-1][j],editMatrix[i-1][j-1]);
            }
        }
        return editMatrix[s1.length()][s2.length()];
    }

}
