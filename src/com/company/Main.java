package com.company;

import com.company.model.Artist;
import com.company.model.Datasource;
import com.company.model.SongArtist;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Cant open db");
            return;
        }

        List<Artist> artists = datasource.queryArtists(Datasource.ORDER_BY_DESC);
        if(artists == null){
            System.out.println("No artists!");
            return;
        }

        for(Artist artist:artists){
            System.out.println("ID = " + artist.getId()+" Name = " + artist.getName());
        }

        List<String> albums = datasource.queryAlbumsForArtist("Pink Floyd",Datasource.ORDER_BY_DESC);

        for(String album:albums){
            System.out.println(album);
        }

        List<SongArtist> songArtists= datasource.queryArtistsForSong("Heartless", Datasource.ORDER_BY_ASC);
        if(songArtists==null){
            System.out.println("Couldnt find the artist for the song");
            return;
        }

        for(SongArtist artist:songArtists){
            System.out.println("Artist name = "+ artist.getArtistName() +
            "Album name = "+ artist.getAlbumName() + " Track = "+artist.getTrack());
        }

        int count = datasource.getCount(Datasource.TABLE_ALBUMS);
        System.out.println(count);

        datasource.createViewForSongArtists();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter some title: ");
        String title = scanner.nextLine();
        songArtists = datasource.querySongInfoView(title);
        if(songArtists.isEmpty()){
            System.out.println("Couldnt find the artist for the song");
            return;
        }

        for(SongArtist songArtist:songArtists){
            System.out.println("FROM VIEW - Artist name = " + songArtist.getArtistName() + " Album name = " + songArtist.getAlbumName() +
            " Track number = "+songArtist.getTrack());
        }

        datasource.insertSong("Touch of Grey","Grateful Dead","In The Dark",1);

        datasource.close();

    }




}


