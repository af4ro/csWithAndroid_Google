/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashSet<String> wordSet = new HashSet<String>();
    private HashMap<String,ArrayList<String>> lettersToWords = new HashMap<String,ArrayList<String>>();
    private HashMap<String,ArrayList<String>> sizeToWords = new HashMap<String,ArrayList<String>>();
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String temp = sortLetters(word);
            ArrayList<String> res = new ArrayList<String>();

            if(lettersToWords.containsKey(temp))
                res = lettersToWords.get(temp);
            res.add(word);
            lettersToWords.put(temp, res);

            ArrayList<String> res2 = new ArrayList<String>();

            if(sizeToWords.containsKey(Integer.toString(word.length())))
                res2 = sizeToWords.get(Integer.toString(word.length()));
            res2.add(word);
            sizeToWords.put(Integer.toString(word.length()), res2);
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(!wordSet.contains(word))
            return false;
        if(word.toLowerCase().contains(base.toLowerCase()))
            return false;
        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sortedTargetWord = sortLetters(targetWord);
        for(int i = 0; i<wordList.size(); i++)
        {
            if (sortLetters(wordList.get(i)).equals(sortedTargetWord)) {
                result.add(wordList.get(i));
            }
        }
        return result;
    }

    public String sortLetters(String word){
        String result = "";
        result+= word.charAt(0);
        for(int i = 1; i<word.length(); ++i)
        {
            int j;
            for(j = result.length()-1; j >=0; j--)
            {
                if (word.charAt(i)>=result.charAt(j))
                    break;
            }
            result = result.substring(0, j+1) + word.charAt(i) + result.substring(j+1, result.length());
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(int i = 0; i<alphabet.length; i++)
        {
            String temp = sortLetters(word+alphabet[i]);
            if(lettersToWords.containsKey(temp)){
                ArrayList<String> res = new ArrayList<>(lettersToWords.get(temp));
                for(int j= 0; j<res.size();j++)
                {
                    if (isGoodWord(res.get(j),word))
                        result.add(res.get(j));
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        Random rand = new Random();
        int index = rand.nextInt(sizeToWords.get(Integer.toString(wordLength)).size());
        String result = "";
        ArrayList<String> words = new ArrayList<String>(sizeToWords.get(Integer.toString(wordLength)));
        for(int i = 0; i< words.size(); i++)
        {
            if(index == words.size())
                index = 0;
            if (getAnagramsWithOneMoreLetter(words.get(index)).size() >= MIN_NUM_ANAGRAMS) {
                result = words.get(index);
                wordLength++;
                break;
            }
            index++;
        }
        return result;
    }
}
