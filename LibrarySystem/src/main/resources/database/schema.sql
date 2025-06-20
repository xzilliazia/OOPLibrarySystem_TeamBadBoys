PGDMP  5                     }            library    17.5    17.5     ;           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            <           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            =           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            >           1262    16533    library    DATABASE     ~   CREATE DATABASE library WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_Indonesia.1252';
    DROP DATABASE library;
                     postgres    false            S           1247    16535 	   user_role    TYPE     E   CREATE TYPE public.user_role AS ENUM (
    'admin',
    'student'
);
    DROP TYPE public.user_role;
       public               postgres    false            �            1259    16539    books    TABLE     �   CREATE TABLE public.books (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    author character varying(100) NOT NULL,
    category character varying(50),
    stock integer,
    CONSTRAINT books_stock_check CHECK ((stock >= 0))
);
    DROP TABLE public.books;
       public         heap r       postgres    false            �            1259    16543    books_id_seq    SEQUENCE     u   CREATE SEQUENCE public.books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.books_id_seq;
       public               postgres    false    217            ?           0    0    books_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.books_id_seq OWNED BY public.books.id;
          public               postgres    false    218            �            1259    16544    borrowed_books    TABLE     @  CREATE TABLE public.borrowed_books (
    id integer NOT NULL,
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    borrow_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    return_date timestamp without time zone,
    status character varying(20) DEFAULT 'PENDING'::character varying NOT NULL
);
 "   DROP TABLE public.borrowed_books;
       public         heap r       postgres    false            �            1259    16549    borrowed_books_id_seq    SEQUENCE     �   CREATE SEQUENCE public.borrowed_books_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.borrowed_books_id_seq;
       public               postgres    false    219            @           0    0    borrowed_books_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.borrowed_books_id_seq OWNED BY public.borrowed_books.id;
          public               postgres    false    220            �            1259    16550    users    TABLE       CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password text NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    role public.user_role NOT NULL,
    major character varying(60)
);
    DROP TABLE public.users;
       public         heap r       postgres    false    851            �            1259    16556    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public               postgres    false    221            A           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public               postgres    false    222            �           2604    16557    books id    DEFAULT     d   ALTER TABLE ONLY public.books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);
 7   ALTER TABLE public.books ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    218    217            �           2604    16558    borrowed_books id    DEFAULT     v   ALTER TABLE ONLY public.borrowed_books ALTER COLUMN id SET DEFAULT nextval('public.borrowed_books_id_seq'::regclass);
 @   ALTER TABLE public.borrowed_books ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    220    219            �           2604    16559    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    222    221            3          0    16539    books 
   TABLE DATA           C   COPY public.books (id, title, author, category, stock) FROM stdin;
    public               postgres    false    217   �       5          0    16544    borrowed_books 
   TABLE DATA           `   COPY public.borrowed_books (id, user_id, book_id, borrow_date, return_date, status) FROM stdin;
    public               postgres    false    219   %       7          0    16550    users 
   TABLE DATA           P   COPY public.users (id, username, password, created_at, role, major) FROM stdin;
    public               postgres    false    221   �       B           0    0    books_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.books_id_seq', 114, true);
          public               postgres    false    218            C           0    0    borrowed_books_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.borrowed_books_id_seq', 13, true);
          public               postgres    false    220            D           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 5, true);
          public               postgres    false    222            �           2606    16561    books books_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.books DROP CONSTRAINT books_pkey;
       public                 postgres    false    217            �           2606    16563 "   borrowed_books borrowed_books_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.borrowed_books
    ADD CONSTRAINT borrowed_books_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.borrowed_books DROP CONSTRAINT borrowed_books_pkey;
       public                 postgres    false    219            �           2606    16565    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    221            �           2606    16567    users users_username_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);
 B   ALTER TABLE ONLY public.users DROP CONSTRAINT users_username_key;
       public                 postgres    false    221            3   }  x�5��n1Eל��$��'�I]۩�8��̆�	���У��}�h!\ݣC6��9�P'<I�	��%#�k��F�wM����(��,ڇ�����q�Q�wt�Qv�Yp�QM^�X��E`a/�V�.Fy��$�,|��)f��}�	k<ú�0r���"e�JԱX��Cptr�h������N�S��$4+l42��5>��G�����z�7+�5M��]g��&��f¸+���g�\"�����
��D��;���]��{�X���]�1�q�$�p&�Ͽy�>�ų��|�?�HO��c?�L��6j��[��T({|��pJ�RF����m��yz�t��6p�aV�x-�,�V���+���n���}UU��      5   �   x���M
�0���)�@e&&�qCPW��9l�TS���=�D�&����Dgy0%�1��tE#�������.9��+���[��ǳ��0�ܧ�V���I�麓��گ�;�����:��N�]�Zh���lNGD|�Qe�      7   �   x�e�9�0D��S�����KI'HcHH,���)��,�"���� �n
q�!�*� θ�YS��P8a���p�]6���K�8S1�$:&(�B����c�]<ͷ��pLD�E0���8�$ڡq�Sk��_�&�Q���r�?�.��5VZ��_�=i)!��EU     