--
-- PostgreSQL database dump
--

-- Dumped from database version 10.7
-- Dumped by pg_dump version 10.7

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: blog; Type: SCHEMA; Schema: -; Owner: myphotos
--

CREATE SCHEMA blog;


ALTER SCHEMA blog OWNER TO myphotos;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: update_rating(); Type: FUNCTION; Schema: blog; Owner: myphotos
--

CREATE FUNCTION blog.update_rating() RETURNS void
    LANGUAGE sql
    AS $$UPDATE blog.profile SET rating = start.rating from
	(SELECT profile_id, sum(views * downloads * 100) as rating FROM blog.photo GROUP BY profile_id) AS start
	WHERE blog.profile.id=start.profile_id;$$;


ALTER FUNCTION blog.update_rating() OWNER TO myphotos;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: accessToken; Type: TABLE; Schema: blog; Owner: myphotos
--

CREATE TABLE blog."accessToken" (
    token character varying NOT NULL,
    profile_id bigint NOT NULL,
    created timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE blog."accessToken" OWNER TO myphotos;

--
-- Name: photo; Type: TABLE; Schema: blog; Owner: myphotos
--

CREATE TABLE blog.photo (
    id bigint NOT NULL,
    small_url character varying(255) NOT NULL,
    large_url character varying(255) NOT NULL,
    original_url character varying(255) NOT NULL,
    views bigint DEFAULT 0 NOT NULL,
    downloads bigint DEFAULT 0 NOT NULL,
    created timestamp with time zone DEFAULT now() NOT NULL,
    profile_id bigint NOT NULL
);


ALTER TABLE blog.photo OWNER TO myphotos;

--
-- Name: photo_seq; Type: SEQUENCE; Schema: blog; Owner: myphotos
--

CREATE SEQUENCE blog.photo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE blog.photo_seq OWNER TO myphotos;

--
-- Name: profile; Type: TABLE; Schema: blog; Owner: myphotos
--

CREATE TABLE blog.profile (
    id bigint NOT NULL,
    uid character varying(255) NOT NULL,
    email character varying(100) NOT NULL,
    first_name character varying(60) NOT NULL,
    last_name character varying(60) NOT NULL,
    avatar_url character varying(255) NOT NULL,
    job_title character varying(100) NOT NULL,
    location character varying(100) NOT NULL,
    photo_count integer DEFAULT 0 NOT NULL,
    created timestamp with time zone DEFAULT now() NOT NULL,
    rating integer DEFAULT 0 NOT NULL
);


ALTER TABLE blog.profile OWNER TO myphotos;

--
-- Name: profile_seq; Type: SEQUENCE; Schema: blog; Owner: myphotos
--

CREATE SEQUENCE blog.profile_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE blog.profile_seq OWNER TO myphotos;

--
-- Data for Name: accessToken; Type: TABLE DATA; Schema: blog; Owner: myphotos
--

COPY blog."accessToken" (token, profile_id, created) FROM stdin;
\.


--
-- Data for Name: photo; Type: TABLE DATA; Schema: blog; Owner: myphotos
--

COPY blog.photo (id, small_url, large_url, original_url, views, downloads, created, profile_id) FROM stdin;
\.


--
-- Data for Name: profile; Type: TABLE DATA; Schema: blog; Owner: myphotos
--

COPY blog.profile (id, uid, email, first_name, last_name, avatar_url, job_title, location, photo_count, created, rating) FROM stdin;
\.


--
-- Name: photo_seq; Type: SEQUENCE SET; Schema: blog; Owner: myphotos
--

SELECT pg_catalog.setval('blog.photo_seq', 1, false);


--
-- Name: profile_seq; Type: SEQUENCE SET; Schema: blog; Owner: myphotos
--

SELECT pg_catalog.setval('blog.profile_seq', 1, false);


--
-- Name: accessToken AccessToken_pkey; Type: CONSTRAINT; Schema: blog; Owner: myphotos
--

ALTER TABLE ONLY blog."accessToken"
    ADD CONSTRAINT "AccessToken_pkey" PRIMARY KEY (token);


--
-- Name: photo Photo_pkey; Type: CONSTRAINT; Schema: blog; Owner: myphotos
--

ALTER TABLE ONLY blog.photo
    ADD CONSTRAINT "Photo_pkey" PRIMARY KEY (id);


--
-- Name: profile Profile_pkey; Type: CONSTRAINT; Schema: blog; Owner: myphotos
--

ALTER TABLE ONLY blog.profile
    ADD CONSTRAINT "Profile_pkey" PRIMARY KEY (id);


--
-- Name: photo profile_FK; Type: FK CONSTRAINT; Schema: blog; Owner: myphotos
--

ALTER TABLE ONLY blog.photo
    ADD CONSTRAINT "profile_FK" FOREIGN KEY (id) REFERENCES blog.profile(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: accessToken profile_FK; Type: FK CONSTRAINT; Schema: blog; Owner: myphotos
--

ALTER TABLE ONLY blog."accessToken"
    ADD CONSTRAINT "profile_FK" FOREIGN KEY (profile_id) REFERENCES blog.profile(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

