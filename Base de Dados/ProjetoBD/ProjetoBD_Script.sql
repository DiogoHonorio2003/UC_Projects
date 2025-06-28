--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

-- Started on 2023-06-16 21:11:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 228 (class 1255 OID 24818)
-- Name: elimina_premium(character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.elimina_premium(usernamep character) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    consumer_data premium_consumer%ROWTYPE;
BEGIN
    SELECT * INTO consumer_data FROM premium_consumer WHERE username = usernameP;
    
    DELETE FROM premium_consumer WHERE username = usernameP;
    
    INSERT INTO consumer (username, password, email, first_name, last_name, user_type)
    VALUES (consumer_data.username, consumer_data.password, consumer_data.email, consumer_data.first_name, consumer_data.last_name, 'consumer'); 

    RETURN 0;
END;
$$;


ALTER FUNCTION public.elimina_premium(usernamep character) OWNER TO postgres;

--
-- TOC entry 229 (class 1255 OID 24804)
-- Name: subscribe_status(character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.subscribe_status(usernamep character) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    endTime timestamp;
BEGIN
    SELECT subscription_end INTO endTime FROM premium_consumer WHERE username = usernameP;
    
    IF endTime <= CURRENT_TIMESTAMP THEN
        -- fim da subscricao
        RETURN 0;
    ELSE
        -- subscricao ativa
        RETURN 1;
    END IF;
END;
$$;


ALTER FUNCTION public.subscribe_status(usernamep character) OWNER TO postgres;

--
-- TOC entry 227 (class 1255 OID 24807)
-- Name: transfer_consumer(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.transfer_consumer() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN


  INSERT INTO premium_consumer (username, password, email, first_name, last_name, user_type)
  VALUES (OLD.username, OLD.password, OLD.email, OLD.first_name, OLD.last_name, 'premium_consumer');

RETURN NULL;
END;
$$;


ALTER FUNCTION public.transfer_consumer() OWNER TO postgres;

--
-- TOC entry 230 (class 1255 OID 24809)
-- Name: triger_elimina_premium(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.triger_elimina_premium() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    status INTEGER;
BEGIN
    -- Call the subscribe_status function and store the result in the status variable
    status := subscribe_status(NEW.owner_username);
    
    IF status = 0 THEN

        PERFORM elimina_premium(NEW.owner_username);
        RETURN NULL;
		
    END IF;
  
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.triger_elimina_premium() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 16485)
-- Name: album; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.album (
    album_name character varying NOT NULL,
    artist_id integer NOT NULL,
    album_id integer NOT NULL,
    songs integer[] NOT NULL,
    release_date timestamp without time zone NOT NULL
);


ALTER TABLE public.album OWNER TO projetouser;

--
-- TOC entry 214 (class 1259 OID 16431)
-- Name: members; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.members (
    username character varying NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    user_type character varying NOT NULL
);


ALTER TABLE public.members OWNER TO projetouser;

--
-- TOC entry 215 (class 1259 OID 16438)
-- Name: artist; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.artist (
    artistic_name character varying NOT NULL,
    label_name character varying,
    artist_id integer NOT NULL
)
INHERITS (public.members);


ALTER TABLE public.artist OWNER TO projetouser;

--
-- TOC entry 219 (class 1259 OID 16507)
-- Name: comment; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.comment (
    song_id character varying NOT NULL,
    post_username character varying NOT NULL,
    comment character varying NOT NULL,
    comment_id integer NOT NULL,
    reply_comment integer
);


ALTER TABLE public.comment OWNER TO projetouser;

--
-- TOC entry 221 (class 1259 OID 16539)
-- Name: consumer; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.consumer (
)
INHERITS (public.members);


ALTER TABLE public.consumer OWNER TO projetouser;

--
-- TOC entry 216 (class 1259 OID 16443)
-- Name: label; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.label (
    label_name character varying NOT NULL
);


ALTER TABLE public.label OWNER TO projetouser;

--
-- TOC entry 220 (class 1259 OID 16526)
-- Name: playlist; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.playlist (
    playlist_id integer NOT NULL,
    visibility character varying NOT NULL,
    playlist_name character varying NOT NULL,
    owner_username character varying NOT NULL,
    songs integer[]
);


ALTER TABLE public.playlist OWNER TO projetouser;

--
-- TOC entry 224 (class 1259 OID 24757)
-- Name: plays; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.plays (
    username character varying NOT NULL,
    songs_played integer[] NOT NULL
);


ALTER TABLE public.plays OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16546)
-- Name: premium_consumer; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.premium_consumer (
    username character varying,
    password character varying,
    email character varying,
    first_name character varying,
    last_name character varying,
    user_type character varying,
    subscription_start timestamp without time zone,
    subscription_end timestamp without time zone,
    subscription_type character varying,
    subscription_cards character varying[]
)
INHERITS (public.members);


ALTER TABLE public.premium_consumer OWNER TO projetouser;

--
-- TOC entry 225 (class 1259 OID 24769)
-- Name: prepaid_card; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prepaid_card (
    number character varying NOT NULL,
    date timestamp without time zone NOT NULL,
    value integer NOT NULL,
    usernamep character varying
);


ALTER TABLE public.prepaid_card OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16471)
-- Name: song; Type: TABLE; Schema: public; Owner: projetouser
--

CREATE TABLE public.song (
    title character varying NOT NULL,
    release_date timestamp without time zone NOT NULL,
    genre character varying NOT NULL,
    times_played integer DEFAULT 0 NOT NULL,
    artist_id integer NOT NULL,
    ismn integer NOT NULL,
    other_artists integer[],
    duration time without time zone NOT NULL
);


ALTER TABLE public.song OWNER TO projetouser;

--
-- TOC entry 226 (class 1259 OID 24786)
-- Name: songs_played; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.songs_played (
    song_id integer NOT NULL,
    date_played date NOT NULL,
    song_genre character varying NOT NULL,
    played_id integer NOT NULL
);


ALTER TABLE public.songs_played OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24750)
-- Name: top10; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.top10 (
    playlist_id character varying NOT NULL,
    owner_username character varying NOT NULL,
    playlist_name character varying NOT NULL,
    visibility character varying NOT NULL,
    songs integer[] NOT NULL
);


ALTER TABLE public.top10 OWNER TO postgres;

--
-- TOC entry 3236 (class 2606 OID 16580)
-- Name: album album_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_pkey PRIMARY KEY (album_id);


--
-- TOC entry 3228 (class 2606 OID 16479)
-- Name: artist artist_fk_song; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_fk_song UNIQUE (artistic_name);


--
-- TOC entry 3230 (class 2606 OID 16559)
-- Name: artist artist_pk; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_pk PRIMARY KEY (artist_id);


--
-- TOC entry 3238 (class 2606 OID 16520)
-- Name: comment commet_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT commet_pkey PRIMARY KEY (comment_id);


--
-- TOC entry 3242 (class 2606 OID 16545)
-- Name: consumer consumer_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.consumer
    ADD CONSTRAINT consumer_pkey PRIMARY KEY (username);


--
-- TOC entry 3232 (class 2606 OID 16449)
-- Name: label label_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.label
    ADD CONSTRAINT label_pkey PRIMARY KEY (label_name);


--
-- TOC entry 3226 (class 2606 OID 16437)
-- Name: members members_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.members
    ADD CONSTRAINT members_pkey PRIMARY KEY (username);


--
-- TOC entry 3240 (class 2606 OID 16573)
-- Name: playlist playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (playlist_id);


--
-- TOC entry 3248 (class 2606 OID 24763)
-- Name: plays plays_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plays
    ADD CONSTRAINT plays_pkey PRIMARY KEY (username);


--
-- TOC entry 3244 (class 2606 OID 16552)
-- Name: premium_consumer premium_consumer_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.premium_consumer
    ADD CONSTRAINT premium_consumer_pkey PRIMARY KEY (username);


--
-- TOC entry 3250 (class 2606 OID 24775)
-- Name: prepaid_card prepaid_card_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prepaid_card
    ADD CONSTRAINT prepaid_card_pkey PRIMARY KEY (number);


--
-- TOC entry 3234 (class 2606 OID 16566)
-- Name: song song_pkey; Type: CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.song
    ADD CONSTRAINT song_pkey PRIMARY KEY (ismn);


--
-- TOC entry 3252 (class 2606 OID 24792)
-- Name: songs_played songs_played_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.songs_played
    ADD CONSTRAINT songs_played_pkey PRIMARY KEY (played_id);


--
-- TOC entry 3246 (class 2606 OID 24756)
-- Name: top10 top10_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.top10
    ADD CONSTRAINT top10_pkey PRIMARY KEY (playlist_id);


--
-- TOC entry 3262 (class 2620 OID 24816)
-- Name: consumer transfer_consumer_trigger; Type: TRIGGER; Schema: public; Owner: projetouser
--

CREATE TRIGGER transfer_consumer_trigger AFTER DELETE ON public.consumer FOR EACH ROW EXECUTE FUNCTION public.transfer_consumer();


--
-- TOC entry 3261 (class 2620 OID 24819)
-- Name: playlist trigger_elimina_premium_; Type: TRIGGER; Schema: public; Owner: projetouser
--

CREATE TRIGGER trigger_elimina_premium_ BEFORE INSERT OR UPDATE ON public.playlist FOR EACH ROW EXECUTE FUNCTION public.triger_elimina_premium();


--
-- TOC entry 3255 (class 2606 OID 16581)
-- Name: album album_fk0; Type: FK CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_fk0 FOREIGN KEY (artist_id) REFERENCES public.artist(artist_id) NOT VALID;


--
-- TOC entry 3253 (class 2606 OID 16452)
-- Name: artist artist_fk1; Type: FK CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_fk1 FOREIGN KEY (label_name) REFERENCES public.label(label_name);


--
-- TOC entry 3256 (class 2606 OID 16596)
-- Name: comment comm_fk2; Type: FK CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comm_fk2 FOREIGN KEY (reply_comment) REFERENCES public.comment(comment_id) NOT VALID;


--
-- TOC entry 3257 (class 2606 OID 16514)
-- Name: comment comment_fk1; Type: FK CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_fk1 FOREIGN KEY (post_username) REFERENCES public.members(username);


--
-- TOC entry 3260 (class 2606 OID 24799)
-- Name: songs_played fk0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.songs_played
    ADD CONSTRAINT fk0 FOREIGN KEY (song_id) REFERENCES public.song(ismn) NOT VALID;


--
-- TOC entry 3259 (class 2606 OID 24776)
-- Name: prepaid_card members; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prepaid_card
    ADD CONSTRAINT members FOREIGN KEY (usernamep) REFERENCES public.members(username) NOT VALID;


--
-- TOC entry 3254 (class 2606 OID 16560)
-- Name: song song_fk1; Type: FK CONSTRAINT; Schema: public; Owner: projetouser
--

ALTER TABLE ONLY public.song
    ADD CONSTRAINT song_fk1 FOREIGN KEY (artist_id) REFERENCES public.artist(artist_id) NOT VALID;


--
-- TOC entry 3258 (class 2606 OID 24764)
-- Name: plays usernadmaslk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.plays
    ADD CONSTRAINT usernadmaslk FOREIGN KEY (username) REFERENCES public.members(username);


--
-- TOC entry 3410 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO projetouser;


-- Completed on 2023-06-16 21:11:22

--
-- PostgreSQL database dump complete
--

