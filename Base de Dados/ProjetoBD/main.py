import psycopg2
import flask
import logging
import jwt
import datetime
from functools import wraps
from collections import Counter
from collections import defaultdict
import random


# http://localhost:8080/members/

StatusCodes = {
    'success': 200,
    'api_error': 400,
    'internal_error': 500
}


def db_connection():
    db = psycopg2.connect(
        user='postgres',
        password='6024',
        host='127.0.0.1',
        port='5432',
        database='projeto'
    )

    return db


app = flask.Flask(__name__)
app.config['SECRET_KEY'] = 'projetoBD'
app.config['SECRET_KEY_ADMIN'] = 'projetoBD_admin'
app.config['SECRET_KEY_ARTIST'] = 'projetoBD_artist'
app.config['SECRET_KEY_PREMIUM'] = 'projetoBD_premium'


def isToken(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = flask.request.headers.get('Token')
        # token = flask.request.args.get('token')
        if not token:
            response = {
                'status': StatusCodes['api_error'], 'results': 'Token is missing'}
            return flask.jsonify(response)

        keys = [app.config["SECRET_KEY"], app.config["SECRET_KEY_ADMIN"],
                app.config["SECRET_KEY_PREMIUM"], app.config["SECRET_KEY_ARTIST"]]

        for key in keys:
            try:
                data = jwt.decode(token, key=key, algorithms=['HS256'])
                logger.debug(f'PUT Login - data: {data}')
                return f(*args, **kwargs)
            except jwt.InvalidTokenError:
                pass

        response = {
            'status': StatusCodes['api_error'], 'results': 'Token is invalid'}
        return flask.jsonify(response)
    return decorated


def isTokenAdmin(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = flask.request.headers.get('Token')
        # token = flask.request.args.get('token')
        if not token:
            response = {
                'status': StatusCodes['api_error'], 'results': 'Token is missing'}
            return flask.jsonify(response)
        try:
            data = jwt.decode(
                token, key=app.config['SECRET_KEY_ADMIN'], algorithms=['HS256'])
            logger.debug(f'PUT Login - data: {data}')
        except:
            response = {'status': StatusCodes['api_error'],
                        'results': 'Nao tem permissoes para criar um novo user'}
            return flask.jsonify(response)
        return f(*args, **kwargs)
    return decorated


def isTokenArtist(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = flask.request.headers.get('Token')
        # token = flask.request.args.get('token')
        if not token:
            response = {
                'status': StatusCodes['api_error'], 'results': 'Token is missing'}
            return flask.jsonify(response)
        try:
            data = jwt.decode(
                token, key=app.config['SECRET_KEY_ARTIST'], algorithms=['HS256'])
            logger.debug(f'PUT Login - data: {data}')
        except:
            response = {
                'status': StatusCodes['api_error'], 'results': 'Nao tem permissoes de artist'}
            return flask.jsonify(response)
        return f(*args, **kwargs)
    return decorated


def isTokenPremium(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = flask.request.headers.get('Token')
        # token = flask.request.args.get('token')
        if not token:
            response = {
                'status': StatusCodes['api_error'], 'results': 'Token is missing'}
            return flask.jsonify(response)
        try:
            data = jwt.decode(
                token, key=app.config['SECRET_KEY_PREMIUM'], algorithms=['HS256'])
            logger.debug(f'PUT Login - data: {data}')
        except:
            response = {
                'status': StatusCodes['api_error'], 'results': 'Nao tem permissoes de premium'}
            return flask.jsonify(response)
        return f(*args, **kwargs)
    return decorated


@app.route('/')
def landing_page():
    return """ """


@app.route('/dbproj/user',  methods=['PUT'])
def login():
    logger.info('Trying to Login in...')
    payload = flask.request.get_json()
    conn = db_connection()
    cur = conn.cursor()

    logger.debug(f'PUT Login - payload: {payload}')

    if 'username' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'username value not in payload'}
        return flask.jsonify(response)
    if 'password' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'password value not in payload'}
        return flask.jsonify(response)

    # Check if user already exists
    cur.execute("SELECT username FROM members WHERE username = %s",
                (payload['username'],))
    isUser = cur.fetchone()

    # Get user_type
    cur.execute("SELECT user_type FROM members WHERE username = %s",
                (payload['username'],))
    user_type = cur.fetchone()

    if not isUser:
        response = {
            'status': StatusCodes['internal_error'], 'results': 'ERRO: username doesnt exist'}
        return flask.jsonify(response)

    # Check if password already exists
    cur.execute("SELECT password FROM members WHERE username = %s AND password = %s",
                (payload['username'], payload['password']))
    isPassword = cur.fetchone()

    if not isPassword:
        response = {
            'status': StatusCodes['internal_error'], 'results': 'ERRO: password doenst match'}
        return flask.jsonify(response)

    # Gerar token
    if "artist" in user_type:
        token = jwt.encode({'username': payload['username'], 'exp': datetime.datetime.utcnow(
        ) + datetime.timedelta(minutes=30)}, key=app.config['SECRET_KEY_ARTIST'])
        logger.debug(f'PUT Login - token: {token}')

        response = {
            'status': StatusCodes['success'],
            'results': "Successfully logged in, your token is: " + token
        }

    elif "premium_consumer" in user_type:
        token = jwt.encode({'username': payload['username'], 'exp': datetime.datetime.utcnow(
        ) + datetime.timedelta(minutes=30)}, key=app.config['SECRET_KEY_PREMIUM'])
        logger.debug(f'PUT Login - token: {token}')

        response = {
            'status': StatusCodes['success'],
            'results': "Successfully logged in, your token is: " + token
        }

    elif "consumer" in user_type:
        token = jwt.encode({'username': payload['username'], 'exp': datetime.datetime.utcnow(
        ) + datetime.timedelta(minutes=30)}, key=app.config['SECRET_KEY'])
        logger.debug(f'PUT Login - token: {token}')

        response = {
            'status': StatusCodes['success'],
            'results': "Successfully logged in, your token is: " + token
        }

    else:
        token = jwt.encode({'username': payload['username'], 'exp': datetime.datetime.utcnow(
        ) + datetime.timedelta(minutes=30)}, key=app.config['SECRET_KEY_ADMIN'])
        logger.debug(f'PUT Login - token: {token}')

        response = {
            'status': StatusCodes['success'],
            'results': "Successfully logged in, your token is: " + token
        }

    return flask.jsonify(response)


# GET MEMBERS
@app.route('/dbproj/members/', methods=['GET'])
@isToken
def list_member():
    logger.info('GET /members')
    conn = db_connection()
    cur = conn.cursor()

    try:
        cur.execute(
            'SELECT username, password, email, first_name, last_name, user_type FROM members')
        rows = cur.fetchall()

        logger.debug('GET /members - parse')
        Results = []
        for row in rows:
            logger.debug(row)

            if "admin" in row[5]:
                content = {'username': row[0], 'password': row[1], 'email': row[2],
                           'first_name': row[3], 'last_name': row[4], 'user_type': row[5]}
                Results.append(content)
            elif "artist" in row[5]:
                cur.execute(
                    'SELECT username, password, email, first_name, last_name, user_type, artistic_name, label_name, artist_id FROM artist WHERE username = %s', (row[0],))
                row_artist = cur.fetchone()
                content = {'username': row_artist[0], 'password': row_artist[1], 'email': row_artist[2], 'first_name': row_artist[3], 'last_name': row_artist[4],
                           'user_type': row_artist[5], 'artistic_name': row_artist[6], 'label_name': row_artist[7], 'artist_id': row_artist[8]}
                Results.append(content)
            elif "premium_consumer" in row[5]:
                cur.execute(
                    'SELECT username, password, email, first_name, last_name, user_type, subscription_type, subscription_start, subscription_end  FROM premium_consumer WHERE username = %s', (row[0],))
                row_pmconsumer = cur.fetchone()
                content = {'username': row_pmconsumer[0], 'password': row_pmconsumer[1], 'email': row_pmconsumer[2], 'first_name': row_pmconsumer[3], 'last_name': row_pmconsumer[4],
                           'user_type': row_pmconsumer[5], 'subscription_type': row_pmconsumer[6], 'subscription_start': row_pmconsumer[7], 'subscription_end': row_pmconsumer[8]}
                Results.append(content)
            elif "consumer" in row[5]:
                content = {'username': row[0], 'password': row[1], 'email': row[2],
                           'first_name': row[3], 'last_name': row[4], 'user_type': row[5]}
                Results.append(content)
            else:
                response = {'status': StatusCodes['internal_error'],
                            'results': 'ERRO: one of the elements are neither consumer, premium_consumer, artist or admin'}
                return flask.jsonify(response)

        response = {'status': StatusCodes['success'], 'results': Results}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'GET /members - error: {error}')
        response = {
            'status': StatusCodes['internal_error'], 'errors': str(error)}

    finally:
        if conn is not None:
            conn.close()

    if 'response' not in locals():
        response = {
            'status': StatusCodes['internal_error'], 'results': 'Unexpected error occurred'}

    return flask.jsonify(response)


# POST MEMBER
@app.route('/dbproj/user/', methods=['POST'])
@isTokenAdmin
def criar_member():
    logger.info('POST /members')
    payload = flask.request.get_json()
    conn = db_connection()
    cur = conn.cursor()
    # Desativar o autocommit
    conn.autocommit = False
    logger.debug(f'POST /members - payload: {payload}')

    # do not forget to validate every argument, e.g.,:
    if 'username' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'username value not in payload'}
        return flask.jsonify(response)
    if 'password' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'password value not in payload'}
        return flask.jsonify(response)
    if 'email' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'email value not in payload'}
        return flask.jsonify(response)
    if 'first_name' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'first_name value not in payload'}
        return flask.jsonify(response)
    if 'last_name' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'last_name value not in payload'}
        return flask.jsonify(response)
    
    # Definir os bloqueios
    cur.execute("LOCK TABLE members IN EXCLUSIVE MODE")

    # Check if username already exist
    cur.execute("SELECT username FROM members WHERE username = %s",
                (payload['username'],))
    isUser = cur.fetchone()
    if isUser:
        response = {'status': StatusCodes['internal_error'],
                    'results': 'ERRO: username already exist'}
        return flask.jsonify(response)

    # if not especified - default: consumer
    if 'user_type' not in payload:
        user_type = "consumer"
        #statement = 'INSERT INTO members (username, password, email, first_name, last_name, user_type) VALUES (%s, %s, %s, %s, %s, %s);'
        #values = (payload['username'], payload['password'], payload['email'],
        #          payload['first_name'], payload['last_name'], user_type)
        
        statement = 'INSERT INTO consumer (username, password, email, first_name, last_name, user_type) VALUES (%s, %s, %s, %s, %s, %s);'
        values = (payload['username'], payload['password'], payload['email'],
                  payload['first_name'], payload['last_name'], user_type)

        #cur.execute(statement2, values2)

    else:
        if "premium_consumer" in payload['user_type']:
            if 'subscription_type' not in payload:
                response = {
                    'status': StatusCodes['api_error'], 'results': 'subscription_type value not in payload'}
                return flask.jsonify(response)
            # VALIDACAO
            if ("month" not in payload['subscription_type']) and ("quarter" not in payload['subscription_type']) and ("semester" not in payload['subscription_type']):
                response = {'status': StatusCodes['internal_error'],
                            'results': 'Subscription type does not exist (mounth, quarter, semester)'}
                return flask.jsonify(response)

            if 'subscription_start' not in payload and 'subscription_end' not in payload:
                if "month" in payload['subscription_type']:
                    utc_fim = datetime.datetime.utcnow() + datetime.timedelta(weeks=4, hours=1)
                elif "quarter" in payload['subscription_type']:
                    utc_fim = datetime.datetime.utcnow() + datetime.timedelta(weeks=12, hours=1)
                elif "semester" in payload['subscription_type']:
                    utc_fim = datetime.datetime.utcnow() + datetime.timedelta(weeks=24, hours=1)

                statement = 'INSERT INTO premium_consumer (username, password, email, first_name, last_name, user_type, subscription_type, subscription_start, subscription_end) VALUES (%s, %s, %s, %s, %s, %s, %s, %s,%s)'
                values = (payload['username'], payload['password'], payload['email'], payload['first_name'], payload['last_name'],
                          payload['user_type'], payload['subscription_type'], datetime.datetime.utcnow() + datetime.timedelta(hours=1), utc_fim)

            elif 'subscription_start' in payload and 'subscription_end' in payload:
                statement = 'INSERT INTO premium_consumer (username, password, email, first_name, last_name, user_type, subscription_type, subscription_start, subscription_end) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)'
                values = (payload['username'], payload['password'], payload['email'], payload['first_name'], payload['last_name'],
                          payload['user_type'], payload['subscription_type'], payload['subscription_start'], payload['subscription_end'])

            else:
                response = {'status': StatusCodes['internal_error'],
                            'results': 'Specify the subscription_start and subscription_end or dont specify at all and we cover that for you.'}
                return flask.jsonify(response)

        elif "admin" in payload['user_type']:
            statement = 'INSERT INTO members (username, password, email, first_name, last_name, user_type) VALUES (%s, %s, %s, %s, %s, %s)'
            values = (payload['username'], payload['password'], payload['email'],
                      payload['first_name'], payload['last_name'], payload['user_type'])

        elif "artist" in payload['user_type']:
            if 'artistic_name' not in payload:
                response = {
                    'status': StatusCodes['api_error'], 'results': 'artistic_name value not in payload'}
                return flask.jsonify(response)
            if 'label_name' not in payload:
                response = {
                    'status': StatusCodes['api_error'], 'results': 'label_name value not in payload'}
                return flask.jsonify(response)
            
            # Get max value of artist_id
            cur.execute("SELECT MAX(artist_id) FROM artist")
            artist_id = cur.fetchone()[0]

            if artist_id is None:
                artist_id = 1
            else:
                artist_id += 1

            statement = 'INSERT INTO artist (username, password, email, first_name, last_name, user_type, artistic_name, label_name, artist_id) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)'
            values = (payload['username'], payload['password'], payload['email'], payload['first_name'],
                      payload['last_name'], payload['user_type'], payload['artistic_name'], payload['label_name'], artist_id)

            # VERIFICAR SE A LABEL EXISTE!!!

        else:
            response = {'status': StatusCodes['internal_error'],
                        'results': 'User type does not exist (consumer, premium_consumer, artist, admin)'}
            return flask.jsonify(response)
        
    try:
        cur.execute(statement, values)
        conn.commit()

        response = {'status': StatusCodes['success'],
                    'results': f'Inserted member {payload["username"]}'}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /members - error: {error}')
        response = {
            'status': StatusCodes['internal_error'], 'errors': str(error)}

        # an error occurred, rollback
        conn.rollback()

    finally:
        if conn is not None:
            conn.close()

    return flask.jsonify(response)

# POST COMMENT
@app.route('/dbproj/comments/<song_id>', methods=['POST'])
@isToken
def add_comment(song_id):
    logger.info('POST /comments')
    payload = flask.request.get_json()
    logger.debug(f'song_id: {song_id}')

    conn = db_connection()
    cur = conn.cursor()

    logger.debug(f'POST /comments - payload: {payload}')

    if 'comment' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'comment value not in payload'}
        return flask.jsonify(response)

    # Tirar o username do token
    keys = [app.config["SECRET_KEY"], app.config["SECRET_KEY_ADMIN"],
            app.config["SECRET_KEY_PREMIUM"], app.config["SECRET_KEY_ARTIST"]]
    
    decoded_token = None
    token = flask.request.headers.get('Token')
    for key in keys:
        try:
            decoded_token = jwt.decode(token, key=key, algorithms=['HS256'])
        except jwt.InvalidTokenError:
            continue
        else:
            break

    if decoded_token is None:
        response = {'status': StatusCodes['api_error'], 'results': 'Invalid or missing token'}
        return flask.jsonify(response)

    username = decoded_token['username']

    # Check if song exists
    cur.execute("SELECT ismn FROM song WHERE ismn = %s", (song_id,))
    if cur.fetchone() is None:
        response = {'status': StatusCodes['api_error'],
                    'results': 'song_id does not exist'}
        return flask.jsonify(response)

    # Get comment id
    cur.execute("SELECT MAX(comment_id) FROM comment")
    comment_id = cur.fetchone()[0]
    if comment_id is None:
        comment_id = 1
    else:
        comment_id = comment_id + 1

    # parameterized queries, good for security and performance
    statement = 'INSERT INTO comment (song_id, comment, comment_id, post_username) VALUES (%s, %s, %s, %s)'
    values = (song_id, payload['comment'], comment_id, username)

    try:
        cur.execute(statement, values)

        # commit the transaction
        conn.commit()
        response = {'status': StatusCodes['success'],
                    'results': f'Inserted comment on song: {song_id}'}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /comment - error: {error}')
        response = {
            'status': StatusCodes['internal_error'], 'errors': str(error)}

        # an error occurred, rollback
        conn.rollback()

    finally:
        if conn is not None:
            conn.close()

    return flask.jsonify(response)


# REPLY COMMENT
@app.route('/dbproj/comments/<song_id>/<parent_comment_id>', methods=['POST'])
@isToken
def add_comment_on_father(song_id, parent_comment_id):
    logger.info('POST /comments')
    payload = flask.request.get_json()
    logger.debug(f'song_id: {song_id}')
    logger.debug(f'parent_comment_id: {parent_comment_id}')

    conn = db_connection()
    cur = conn.cursor()

    logger.debug(f'POST /comments - payload: {payload}')

    if 'comment' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'comment value not in payload'}
        return flask.jsonify(response)
    
    # Tirar o username do token
    keys = [app.config["SECRET_KEY"], app.config["SECRET_KEY_ADMIN"],
            app.config["SECRET_KEY_PREMIUM"], app.config["SECRET_KEY_ARTIST"]]
    
    decoded_token = None
    token = flask.request.headers.get('Token')
    for key in keys:
        try:
            decoded_token = jwt.decode(token, key=key, algorithms=['HS256'])
        except jwt.InvalidTokenError:
            continue
        else:
            break

    if decoded_token is None:
        response = {'status': StatusCodes['api_error'], 'results': 'Invalid or missing token'}
        return flask.jsonify(response)

    username = decoded_token['username']

    # Get comment id
    cur.execute("SELECT MAX(comment_id) FROM comment")
    comment_id = cur.fetchone()[0]
    if comment_id is None:
        comment_id = 1
    else:
        comment_id = comment_id + 1

    # Check if parent_comment_id is comment_id of song_id
    cur.execute("SELECT comment_id FROM comment WHERE song_id = %s AND comment_id = %s", (song_id, parent_comment_id))
    if cur.fetchone() is None:
        response = {'status': StatusCodes['api_error'],
                    'results': 'parent_comment_id does not exist'}
        return flask.jsonify(response)
    
    # Check if parent_comment_id already have reply_comment
    cur.execute("SELECT reply_comment FROM comment WHERE comment_id = %s AND reply_comment IS NOT NULL", (parent_comment_id,))
    if cur.fetchone() is not None:
        response = {'status': StatusCodes['api_error'],
                    'results': 'parent_comment_id already has a reply_comment'}
        return flask.jsonify(response)
 
    # parameterized queries, good for security and performance
    statement = 'INSERT INTO comment (song_id, comment, comment_id, post_username) VALUES (%s, %s, %s, %s);UPDATE comment SET reply_comment = %s WHERE comment_id = %s'
    values = (song_id, payload['comment'], comment_id, username, comment_id, parent_comment_id)

    try:
        cur.execute(statement, values)

        # commit the transaction
        conn.commit()
        response = {'status': StatusCodes['success'],
                    'results': f'Inserted reply comment on song: {song_id}'}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /comment - error: {error}')
        response = {
            'status': StatusCodes['internal_error'], 'errors': str(error)}

        # an error occurred, rollback
        conn.rollback()

    finally:
        if conn is not None:
            conn.close()

    return flask.jsonify(response)


# INSERIR LABELS
@app.route('/dbproj/label/', methods=['POST'])
@isTokenAdmin
def add_label():
    logger.info('POST /label')
    payload = flask.request.get_json()
    conn = db_connection()
    cur = conn.cursor()
    logger.debug(f'POST /label - payload: {payload}')

    if 'label_name' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'label_name value not in payload'}
        return flask.jsonify(response)

    statement = 'INSERT INTO label (label_name) VALUES (%s)'
    values = (payload['label_name'],)

    try:
        cur.execute(statement, values)
        conn.commit()

        response = {'status': StatusCodes['success'],
                    'results': f'Inserted label {payload["label_name"]}'}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /members - error: {error}')
        response = {
            'status': StatusCodes['internal_error'], 'errors': str(error)}

        # an error occurred, rollback
        conn.rollback()

    finally:
        if conn is not None:
            conn.close()

    return flask.jsonify(response)


# CREATE PLAYLIST
@app.route('/dbproj/playlist/', methods=['POST'])
@isTokenPremium
def create_playlist():
    logger.info('POST /playlist')
    payload = flask.request.get_json()
    conn = db_connection()
    cur = conn.cursor()
    logger.debug(f'POST /playlist - payload: {payload}')

    if 'playlist_name' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'playlist_name value not in payload'}
        return flask.jsonify(response)

    if 'visibility' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'visibility value not in payload'}
        return flask.jsonify(response)

    if 'songs' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'songs value not in payload'}
        return flask.jsonify(response)
    
    if ("private" not in payload["visibility"]) and ("public" not in payload["visibility"]):
        response = {'status': StatusCodes['api_error'], 'results': 'Visibility must be private or public'}
        return flask.jsonify(response)

    for song in payload['songs']:
        # Check if song exists
        cur.execute("SELECT ismn FROM song WHERE ismn = %s", (song))
        isSong = cur.fetchone()
        if not isSong:
            response = {
                'status': StatusCodes['api_error'], 'results': 'Song id: {song} doenst exist'}
            return flask.jsonify(response)

    # Tirar o username do token
    token = flask.request.headers.get('Token')
    decoded_token = jwt.decode(token, key=app.config['SECRET_KEY_PREMIUM'], algorithms=['HS256'])
    username = decoded_token['username']
    
    # Get max value of artist_id
    cur.execute("SELECT MAX(playlist_id) FROM playlist")
    playlist_id = cur.fetchone()[0]
    if playlist_id is None:
        playlist_id = 1
    else:
        playlist_id += 1

    songs = [int(song_id) for song_id in payload['songs']]

    statement = 'INSERT INTO playlist (playlist_id, owner_username, playlist_name, visibility, songs) VALUES (%s, %s, %s, %s, %s)'
    values = (playlist_id, username, payload['playlist_name'], payload['visibility'], songs)

    try:
        cur.execute(statement, values)
        conn.commit()

        cur.execute('SELECT playlist_id from playlist WHERE playlist_id=%s', (playlist_id,))
        result = cur.fetchone()

        if result is None:
            response = {'status': StatusCodes['success'],
                    'results': f'Did not insert playlist {payload["playlist_name"]} successfully'}
        else:
            response = {'status': StatusCodes['success'],
                    'results': f'Inserted playlist {payload["playlist_name"]} successfully. Your playlist id is {playlist_id}'}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /playlist - error: {error}')
        response = {
            'status': StatusCodes['internal_error'], 'errors': str(error)}

        # an error occurred, rollback
        conn.rollback()

    finally:
        if conn is not None:
            conn.close()

    return flask.jsonify(response)


# ADD SONG
@app.route('/dbproj/song/', methods=['POST'])
@isTokenArtist
def add_song():
    logger.info('POST /song')
    payload = flask.request.get_json()
    conn = db_connection()
    cur = conn.cursor()
    # Desativar o autocommit
    conn.autocommit = False
    logger.debug(f'POST /song - payload: {payload}')

    
    # do not forget to validate every argument, e.g.,:
    if 'title' not in payload:
        response = {'status': StatusCodes['api_error'], 'results': 'title value not in payload'}
        return flask.jsonify(response)
    if 'duration' not in payload:
        response = {'status': StatusCodes['api_error'], 'results': 'duration value not in payload'}
        return flask.jsonify(response)
    if 'genre' not in payload:
        response = {'status': StatusCodes['api_error'], 'results': 'genre value not in payload'}
        return flask.jsonify(response)

    # Definir os bloqueios
    cur.execute("LOCK TABLE song IN EXCLUSIVE MODE")

    cur.execute("SELECT MAX(ismn) FROM song")
    max_valor = cur.fetchone()[0]

    if max_valor is None:
        max_valor = 1
    else:
        max_valor = max_valor + 1

    cur.execute("SELECT artist_id FROM artist WHERE artist_id = %s", (payload['artist_id'],))
    hasArtist = cur.fetchone()

    if not hasArtist:
        response = {'status': StatusCodes['internal_error'], 'results': 'ERRO: Artist doesnt exist'}
        return flask.jsonify(response)
    
    other_artist_ids = payload['other_id']
    for other_id in other_artist_ids:
        cur.execute("SELECT artist_id FROM artist WHERE artist_id = %s", (other_id,))
        hasArtist = cur.fetchone()

        if not hasArtist:
            response = {'status': StatusCodes['internal_error'], 'results': 'ERRO: Collab Artist doesnt exist'}
            return flask.jsonify(response)

    token = flask.request.headers.get('Token')
    decoded_token = jwt.decode(token, key=app.config['SECRET_KEY_ARTIST'], algorithms=['HS256'])
    username = decoded_token['username']

    cur.execute("SELECT username FROM artist WHERE artist_id = %s", (payload['artist_id'],))
    username_aux = cur.fetchone()[0]
    
    if username != username_aux:
        response = {'status': StatusCodes['internal_error'], 'results': 'ERRO: the token artist is not the same as the artist'}
        return flask.jsonify(response)

    # parameterized queries, good for security and performance
    if 'release_date' not in payload:
        release_date = datetime.datetime.utcnow() + datetime.timedelta(hours=1)
        statement = 'INSERT INTO song (title, ismn, duration, release_date, genre, artist_id, times_played, other_artists) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'
        values = (payload['title'], max_valor, payload['duration'], release_date, payload['genre'], payload['artist_id'], 0, payload['other_id'])
    else:
        statement = 'INSERT INTO song (title, ismn, duration, release_date, genre, artist_id, times_played, other_artists) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'
        values = (payload['title'], max_valor, payload['duration'], payload['release_date'], payload['genre'], payload['artist_id'], 0, payload['other_id'])

    try:
        cur.execute(statement, values)
        # commit the transaction
        conn.commit()
        response = {'status': StatusCodes['success'], 'results': f'Inserted the song {payload["title"]}'}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /members - error: {error}')
        response = {'status': StatusCodes['internal_error'], 'errors': str(error)}

        # an error occurred, rollback
        conn.rollback()

    finally:
        if conn is not None:
            conn.close()

    return flask.jsonify(response)

# CREATE ALBUM
@app.route('/dbproj/album/', methods=['POST'])
@isTokenArtist
def create_album():
    logger.info('POST /album')
    payload = flask.request.get_json()
    conn = db_connection()
    cur = conn.cursor()
    logger.debug(f'POST /album - payload: {payload}')
    
    if 'album_name' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'album_name value not in payload'}
        return flask.jsonify(response)
    if 'release_date' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'release_date value not in payload'}
        return flask.jsonify(response)
    if 'artist_id' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'artist_id value not in payload'}
        return flask.jsonify(response)
    if 'songs' not in payload:
        response = {'status': StatusCodes['api_error'],
                    'results': 'songs value not in payload'}
        return flask.jsonify(response)
    
    array_song_id = list()

    for song in payload['songs']:
        # Check if song exists
        if ('title' not in song) and ('duration' not in song) and ('release_date' not in song) and ('genre' not in song) and ('artist_id' not in song) and ('other_artists' not in song):
            cur.execute("SELECT ismn FROM song WHERE ismn = %s", (song[0]))
            isSong = cur.fetchone()
            if not isSong:
                response = {
                    'status': StatusCodes['api_error'], 'results': 'Song id: {song} doenst exist'}
                return flask.jsonify(response)
            
            array_song_id.append([isSong[0]])

        else:
            # If its a new song
            if 'title' not in song:
                response = {'status': StatusCodes['api_error'],
                            'results': 'title value not in payload'}
                return flask.jsonify(response)
            if 'duration' not in song:
                response = {'status': StatusCodes['api_error'],
                            'results': 'duration value not in payload'}
                return flask.jsonify(response)
            if 'release_date' not in song:
                response = {'status': StatusCodes['api_error'],
                            'results': 'release date value not in payload'}
                return flask.jsonify(response)
            if 'genre' not in song:
                response = {'status': StatusCodes['api_error'],
                            'results': 'genre value not in payload'}
                return flask.jsonify(response)
            if 'artist_id' not in song:
                response = {'status': StatusCodes['api_error'],
                            'results': 'artist_id name value not in payload'}
                return flask.jsonify(response)
            
            # Get max value of ismn
            cur.execute("SELECT MAX(ismn) FROM song")
            ismn = cur.fetchone()[0]
            if ismn is None:
                ismn = 1
            else:
                ismn += 1
            
            times_played = 0

            # Format duration and release_date
            duration = datetime.datetime.strptime(song['duration'], "%M:%S").time()
            release_date = datetime.datetime.strptime(song['release_date'], "%d/%m/%Y %H:%M:%S")
            release_date = datetime.datetime.combine(release_date.date(), datetime.time())


            if 'other_artists' in song:
                # Check if artist is valid artist
                for artist in song['other_artists']:
                    cur.execute("SELECT artist_id FROM artist WHERE artist_id = %s", (artist))
                    artist_id = cur.fetchone()[0]
                    if artist_id is None:
                        response = {
                            'status': StatusCodes['api_error'], 'results': 'Artist id: {artist} doenst exist'}
                        return flask.jsonify(response)
                
                
                other_artists = [int(artist_id) for artist_id in song['other_artists']]

                # Add the song to song table
                statement_song = 'INSERT INTO song (ismn, title, duration, release_date, genre, artist_id, times_played, other_artists) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'
                values_song = (ismn, song['title'], duration, release_date, song['genre'], song['artist_id'], times_played, other_artists)
                
            else:
                # Add the song to song table
                statement_song = 'INSERT INTO song (ismn, title, duration, release_date, genre, artist_id, times_played) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'
                values_song = (ismn, song['title'], duration, release_date, song['genre'], song['artist_id'], times_played)
                
            try:
                cur.execute(statement_song, values_song)
                conn.commit()
            except (Exception, psycopg2.DatabaseError) as error:
                logger.error(f'POST /album/song - error: {error}')
                response = {'status': StatusCodes['internal_error'], 'errors': str(error)}
                conn.rollback()
                return flask.jsonify(response)

            array_song_id.append([ismn])
    

    # Get max value of album_id
    cur.execute("SELECT MAX(album_id) FROM album")
    album_id = cur.fetchone()[0]
    if album_id is None:
        album_id = 1
    else:
        album_id += 1
    
    # Format release_date
    release_date = datetime.datetime.strptime(payload['release_date'], "%d/%m/%Y %H:%M:%S")
    release_date = datetime.datetime.combine(release_date.date(), datetime.time())


    statement = 'INSERT INTO album (album_id, album_name, release_date, artist_id, songs) VALUES (%s, %s, %s, %s, %s)'
    values = (album_id, payload['album_name'], release_date, payload['artist_id'], array_song_id)

    try:
        cur.execute(statement, values)
        conn.commit()
        response = {'status': StatusCodes['success'],
                    'results': f'Inserted album {payload["album_name"]} successfully. Your album id is {album_id}'}
    
    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /album/song - error: {error}')
        response = {'status': StatusCodes['internal_error'], 'errors': str(error)}
        conn.rollback()
        return flask.jsonify(response)
    
    finally:
        if conn is not None:
            conn.close()
    
    return flask.jsonify(response)

# DETAIL ARTIST
@app.route('/dbproj/artist_info/<artist_id>', methods=['GET'])
@isToken
def detail_artist(artist_id):


    logger.info('GET /artist_info')
    #payload = flask.request.get_json()

    conn = db_connection()
    cur = conn.cursor()


    #logger.debug(f'POST /artist_info - payload: {payload}')
    logger.debug(f'artist_id: {artist_id}')

    if artist_id is None:
        response = {'status': StatusCodes['api_error'], 'results': 'artist_id has no value'}
        return flask.jsonify(response)
    
    array_songs_ids=[]
    array_albuns_ids=[]
    array_playlists_ids=[]
    try:
        cur.execute('SELECT ismn FROM song WHERE artist_id = %s', (artist_id,))
        songs_ids = cur.fetchall()

        array_songs_ids = [song_id[0] for song_id in songs_ids]
        array_albuns_ids = []
        array_playlists_ids = []

        for song_id in array_songs_ids:
            
            cur.execute('SELECT playlist_id FROM playlist WHERE %s=ANY(songs)', (song_id,))
            playlists_ids = cur.fetchall()

            array_playlists_ids += [playlist_id[0] for playlist_id in playlists_ids]

            cur.execute('SELECT album_id FROM album WHERE %s=ANY(songs)', (song_id,))
            albums_ids = cur.fetchall()

            array_albuns_ids += [album_id[0] for album_id in albums_ids]

        conn.commit()

        response = {
            'status': StatusCodes['success'],
            'songs_ids': array_songs_ids,
            'albums_ids': array_albuns_ids,
            'playlists_ids': array_playlists_ids
        }

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /detail - error: {error}')
        response = {'status': StatusCodes['internal_error'], 'errors': str(error)}
        conn.rollback()

    conn.close()
    return flask.jsonify(response)

# SEARCH SONG
@app.route('/dbproj/song/<title>', methods=['GET'])
@isToken
def search_song(title):
    logger.info('GET /song')
    logger.debug(f'title: {title}')

    conn = db_connection()
    cur = conn.cursor()
    
    array_artist_ids=[]
    array_albums_ids=[]
    try:
        cur.execute('SELECT * FROM song WHERE title ILIKE %s', (('%' + title + '%'),))
        rows = cur.fetchall()

        logger.debug('GET /song - parse')
        Results = []
        for row in rows:
            duration = row[7].strftime("%H:%M:%S") 
            release_date = row[1]  
            release_date = datetime.datetime.combine(release_date.date(), release_date.time())
            logger.debug(row)
            
            artist = row[4]
            other_artists = row[6]

            cur.execute("SELECT artistic_name FROM artist WHERE artist_id = %s", (artist,))
            artistic_name = cur.fetchone()[0]

            array_artist_ids.append(artistic_name)

            cur.execute("SELECT artistic_name FROM artist WHERE artist_id = ANY(%s::integer[])", (other_artists,))
            collab_artistic_names = cur.fetchall()

            array_artist_ids += [name[0] for name in collab_artistic_names]

            if array_artist_ids == []:
                response = {'status': StatusCodes['api_error'], 'results': 'There is no artists'}
                return flask.jsonify(response)


            cur.execute("SELECT album_id FROM album WHERE %s = ANY(songs)", (row[3],))
            albums_ids = cur.fetchall()

            array_albums_ids += [album_id[0] for album_id in albums_ids]

            if array_albums_ids == []:
                content =  {'title': row[0], 'duration': duration, 'genre': row[2], 'times_played': row[3], 'release_date': release_date, 'ismn': row[5], 'artists': array_artist_ids, 'albums': "There is no albums with this song"}
                Results.append(content)

            else:
                content = {'title': row[0], 'duration': duration, 'genre': row[2], 'times_played': row[3], 'release_date': release_date, 'ismn': row[5], 'artists': array_artist_ids, 'albums': array_albums_ids}
                Results.append(content)

            array_artist_ids=[]
            array_albums_ids=[]

        if Results == []:
            response = {'status': StatusCodes['api_error'], 'results': 'There is no songs with that title'}
            return flask.jsonify(response)
        else:
            response = {'status': StatusCodes['success'], 'results': Results}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'GET /song - error: {error}')
        response = {'status': StatusCodes['internal_error'], 'errors': str(error)}

    finally:
        if conn is not None:
            conn.close()

    if 'response' not in locals():
        response = {'status': StatusCodes['internal_error'], 'results': 'Unexpected error occurred'}

    return flask.jsonify(response)

# PLAY SONG
def top_10_numeros_frequentes(array):
    contador = Counter(array)
    top_10 = contador.most_common(10)
    numeros_frequentes = [numero for numero, _ in top_10]
    return numeros_frequentes

@app.route('/dbproj/<song_id>', methods=['PUT'])
@isToken
def play_song(song_id):
    logger.info('PUT/ times_played')

    conn = db_connection()
    cur = conn.cursor()
    conn.autocommit = False
    
    #logger.debug(f'POST /artist_info - payload: {payload}')
    logger.debug(f'song_id: {song_id}')

    if song_id is None:
        response = {'status': StatusCodes['api_error'], 'results': 'song_id has no value'}
        return flask.jsonify(response)

    songs = []
    try:
        cur.execute('SELECT times_played FROM song WHERE ismn = %s', (song_id,))
        times_played = cur.fetchone()[0]

        times_played += 1

        cur.execute('UPDATE song SET times_played = %s WHERE ismn = %s', (times_played, song_id))
        conn.commit()

        # Tirar o username do token
        keys = [app.config["SECRET_KEY"], app.config["SECRET_KEY_ADMIN"],
                app.config["SECRET_KEY_PREMIUM"], app.config["SECRET_KEY_ARTIST"]]
        
        decoded_token = None
        token = flask.request.headers.get('Token')
        for key in keys:
            try:
                decoded_token = jwt.decode(token, key=key, algorithms=['HS256'])
            except jwt.InvalidTokenError:
                continue
            else:
                break

        if decoded_token is None:
            response = {'status': StatusCodes['api_error'], 'results': 'Invalid or missing token'}
            return flask.jsonify(response)

        username = decoded_token['username']

        cur.execute('SELECT playlist_id FROM top10 WHERE owner_username = %s', (username,))
        playlist_id = cur.fetchone()

        # Get genre of song_id
        cur.execute("SELECT genre FROM song WHERE ismn = %s", (song_id,))
        genre = x = cur.fetchone()[0]
        
        # Max valor of played songs
        cur.execute("SELECT MAX(played_id) FROM songs_played")
        x = cur.fetchone()[0]
        if x is None:
            x = 1
        else:
            x = x + 1
        statement_songs_played = "insert INTO songs_played (song_id, date_played, song_genre, played_id) VALUES(%s, %s, %s, %s)"
        values_songs_played = (int(song_id), datetime.datetime.now(),genre,x)
        cur.execute(statement_songs_played, values_songs_played)

        if playlist_id == None:
            playlist_name = username + "top10"
            songs.append(int(song_id))
            cur.execute("SELECT MAX(playlist_id) FROM playlist")
            max_valor = cur.fetchone()[0]

            if max_valor is None:
                max_valor = 1
            else:
                max_valor = max_valor + 1

            statement_playlist = 'INSERT INTO playlist (playlist_id, owner_username, playlist_name, visibility, songs) VALUES (%s, %s, %s, %s, %s)'
            values_playlist = (max_valor, username, playlist_name , "public", songs)

            statement_top10 = 'INSERT INTO top10 (playlist_id, owner_username, playlist_name, visibility, songs) VALUES (%s, %s, %s, %s, %s)'
            values_top10 = (max_valor, username, playlist_name , "public", songs)

            songs_played = [int(song_id)]
            statement_plays = 'INSERT INTO plays (username,songs_played) VALUES(%s, %s)'
            values_plays = (username, songs_played)

             

            cur.execute(statement_playlist, values_playlist)
            cur.execute(statement_top10, values_top10)
            cur.execute(statement_plays, values_plays)
            conn.commit()

        else:
            cur.execute('SELECT songs_played FROM plays WHERE username = %s', (username,))
            songs_played= cur.fetchone()[0]
            songs_played.append(int(song_id))
            songs = top_10_numeros_frequentes(songs_played)

            cur.execute('UPDATE plays SET songs_played = %s WHERE username = %s', (([int(num) for num in songs_played], username)))
            cur.execute('UPDATE top10 SET songs = %s WHERE owner_username = %s', (([int(num) for num in songs], username)))
            cur.execute('UPDATE playlist SET songs = %s WHERE owner_username = %s', (([int(num) for num in songs], username)))
            conn.commit()

        response = {'status': StatusCodes['success'], 'results': 'times_played increased'}
        return flask.jsonify(response)

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /members - error: {error}')
        response = {'status': StatusCodes['internal_error'], 'errors': str(error)}
        conn.rollback()

    conn.close()
    return flask.jsonify(response)

# SUBSCRIBE
@app.route('/dbproj/subcription', methods=['POST'])
@isToken
def subscribe_premium():
    logger.info('POST /subcription')
    payload = flask.request.get_json()

    conn = db_connection()
    cur = conn.cursor()

    logger.debug(f'POST /subcription - payload: {payload}')


    if 'period' not in payload:
        response = {'status': StatusCodes['api_error'], 'results': 'period value not in payload'}
        return flask.jsonify(response)
    if 'cards' not in payload:
        response = {'status': StatusCodes['api_error'], 'results': 'cards value not in payload'}
        return flask.jsonify(response)

    if(payload['period']!='month' and payload['period']!='quarter' and payload['period']!='semester'):
        response = {'status': StatusCodes['api_error'], 'results': 'period must be month or quarter or semester'}
        return flask.jsonify(response)


    #validacao
    total_value=0
    for card in payload['cards']:
        cur.execute('SELECT number, date, value, usernamep FROM prepaid_card where number= %s FOR UPDATE',(card,))
        result = cur.fetchone()

        if result:
            card_number = result[0]
            data = result[1]
            value = result[2]
            usernamec = result[3]
            total_value += value
        else:
            response = {'status': StatusCodes['api_error'], 'results': f'Card não existe'}
            return flask.jsonify(response)

        #ver se tem valor None
        if card_number is None or data is None or value is None:
            response = {'status': StatusCodes['api_error'], 'results': 'card as value/s None'}
            return flask.jsonify(response)
        
        #ver se não existe
        else:
            cur.execute('SELECT number FROM prepaid_card WHERE number = %s', (card_number,))
            number_search = cur.fetchone()

            if number_search is None:
                response = {'status': StatusCodes['api_error'], 'results': f'card does not exist'}
                return flask.jsonify(response)

        #se existir
        
        # Tirar o username do token
        keys = [app.config["SECRET_KEY"], app.config["SECRET_KEY_ADMIN"],
        app.config["SECRET_KEY_PREMIUM"], app.config["SECRET_KEY_ARTIST"]]
    
        decoded_token = None
        token = flask.request.headers.get('Token')
        for key in keys:
            try:
                decoded_token = jwt.decode(token, key=key, algorithms=['HS256'])
            except jwt.InvalidTokenError:
                continue
            else:
                break

        if decoded_token is None:
            response = {'status': StatusCodes['api_error'], 'results': 'Invalid or missing token'}
            return flask.jsonify(response)

        username = decoded_token['username']
        #cur.execute('SELECT * from members where username=%s',('consumerzao',))
        #result = cur.fetchall()
        #return result

        #ver se está disponivel ou se ja lhe pertence: username="NULL" ou =decoded_token['username']
        if usernamec is not None and usernamec!=username :
            response = {'status': StatusCodes['api_error'], 'results': 'cartão pertence a outro consumer'}
            return flask.jsonify(response)



        cur.execute('SELECT username FROM members WHERE username = %s', (username,))
        result = cur.fetchone()

        if result is None:
            response = {'status': StatusCodes['api_error'], 'results': 'Username not found in members table'}
            return flask.jsonify(response)

    #pagamento
    valor_subs=0
    
    if payload['period']== 'month':
        valor_subs=7
    if payload['period']== 'quarter':
        valor_subs=21
    if payload['period']== 'semester':
        valor_subs=42

            
    times_played = 0

    #nao ter dinheiro suficiente
    if total_value < valor_subs:
        response = {'status': StatusCodes['api_error'], 'results': f'the money is not enough'}
        return flask.jsonify(response)

    #caso tenha, fazer pagamento
    for card in payload['cards']:
        cur.execute('SELECT value FROM prepaid_card WHERE number = %s', (card,))
        value = cur.fetchone()[0]

        if value >= valor_subs:
            value=value - valor_subs
            valor_subs=0
            cur.execute('UPDATE prepaid_card SET value = %s WHERE number = %s', (value, card,))
            
        elif value < valor_subs:
            valor_subs=valor_subs-value
            value=0
            cur.execute('UPDATE prepaid_card SET value = %s WHERE number = %s', (value, card,))

    #criar a subscricao

    if "month" in payload['period']:
        utc_fim = datetime.datetime.utcnow() + datetime.timedelta(weeks=4, hours=1)
        #utc_fim = datetime.datetime.utcnow() + datetime.timedelta(seconds=1)
    elif "quarter" in payload['period']:
        utc_fim = datetime.datetime.utcnow() + datetime.timedelta(weeks=12, hours=1)
    elif "semester" in payload['period']:
        utc_fim = datetime.datetime.utcnow() + datetime.timedelta(weeks=24, hours=1)
    

    ###################tirar o member do consumer e mete-o nos premium#############################
    cur.execute('SELECT username, password, email, first_name, last_name, user_type FROM members where username= %s',(username,)) #ficar com valores
    result = cur.fetchone()  
    if result is None:
        cur.execute('SELECT username, password, email, first_name, last_name, user_type FROM premium_consumer where username= %s',(username,)) #ver se ja esta inscrito
        result1 = cur.fetchone()
        if result1 is None:
            response = {'status': StatusCodes['api_error'], 'results': 'Consumer nao exite'}
            return flask.jsonify(response) 
        else:
            response = {'status': StatusCodes['api_error'], 'results': 'Consumer já está subscrito'}
            return flask.jsonify(response)
        
    
    username=result[0]

    cur.execute('DELETE FROM members WHERE username = %s', (username,))
    cur.execute('DELETE FROM consumer WHERE username = %s', (username,)) #eliminar consumer
    subscription_cards_int = [card for card in payload["cards"]]

    try:
        cur.execute('UPDATE premium_consumer SET subscription_start = %s, subscription_end = %s, subscription_type = %s, subscription_cards = %s WHERE username = %s',
            (datetime.datetime.utcnow(),utc_fim, payload['period'],subscription_cards_int, username))
        cur.execute('UPDATE members SET user_type = %s WHERE username = %s', ("premium_consumer", username,))
        conn.commit()
        response = {'status': StatusCodes['success'], 'results': f'Subscricao efetuada com sucesso'}

    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'POST /members - error: {error}')
        response = {'status': StatusCodes['internal_error'], 'errors': str(error)}

        # an error occurred, rollback
        conn.rollback()

    finally:
        if conn is not None:
            conn.close()

    return flask.jsonify(response)

# ADD CARD
def generate_card_number():
    card_number = ''.join(random.choices('0123456789', k=4))
    return card_number

@app.route('/dbproj/card/', methods=['POST'])
@isTokenAdmin
def create_card():
    logger.info('POST /card')
    payload = flask.request.get_json()

    conn = db_connection()
    cur = conn.cursor()

    logger.debug(f'POST /card - payload: {payload}')

    if 'number_cards' not in payload:
        response = {'status': StatusCodes['api_error'], 'results': 'number_cards value not in payload'}
        return flask.jsonify(response)
    if 'card_price' not in payload:
        response = {'status': StatusCodes['api_error'], 'results': 'card_price value not in payload'}
        return flask.jsonify(response)

    ###  verificar card_price  ###
    if(payload['card_price']!=10 and payload['card_price']!=25 and payload['card_price']!=50):
        response = {'status': StatusCodes['api_error'], 'results': 'card_price tem de ter valor 10,25 ou 50'}
        return flask.jsonify(response)
    
    
    response = {'status': StatusCodes['success'], 'results': []}
    
    for _ in range(int(payload['number_cards'])):
        number=""
        for i in range(4):
            number = number + generate_card_number()+" "
        number.rstrip()
        cur.execute("SELECT number FROM prepaid_card WHERE number = %s", (number,))
        existing_card = cur.fetchone()

        if existing_card:
            continue  

        limit_date = datetime.datetime.utcnow() + datetime.timedelta(hours=1)

        token = flask.request.headers.get('Token')
        decoded_token = jwt.decode(token, algorithms=['HS256'], options={"verify_signature": False})
        username = decoded_token['username']

        statement = "INSERT INTO prepaid_card (number, date, value) VALUES (%s, %s, %s)"
        values = (number, limit_date, int(payload['card_price']))

        try:
            cur.execute(statement, values)
            conn.commit()
            response['results'].append({'card_number': number})

        except (Exception, psycopg2.DatabaseError) as error:
            logger.error(f'POST /members - error: {error}')
            response = {'status': StatusCodes['internal_error'], 'errors': str(error)}
            conn.rollback()

    conn.close()
    return flask.jsonify(response)

# REPORT
@app.route('/dbproj/report/<year>-<month>/', methods=['GET'])
@isToken
def get_report(year,month):
    logger.info('GET /report')
    conn = db_connection()
    cur = conn.cursor()
    logger.debug(f'GET /report - year: {year}, month: {month}')

    data = f'{year}-{month}-01'

    try:
        statement = "SELECT * FROM songs_played WHERE date_played >= (%s::date - INTERVAL '1 year') AND date_played <= %s::date"
        values = (data, data)
        cur.execute(statement, values)
        dados = cur.fetchall()
        conn.commit()

        results = defaultdict(lambda: defaultdict(int))
        for row in dados:
            date = row[1].strftime('%Y-%m-%d')
            genre = row[2]
            results[date][genre] += 1
        
        response = {'status': StatusCodes['success'], 'results': dict(results)}
    except (Exception, psycopg2.DatabaseError) as error:
        logger.error(f'GET /report - error: {error}')
        response = {'status': StatusCodes['internal_error'], 'errors': str(error)}
        conn.rollback()
    finally:
        if conn is not None:
            conn.close()
        return flask.jsonify(response)

if __name__ == '__main__':

    # set up logging
    logging.basicConfig(filename='log_file.log')
    logger = logging.getLogger('logger')
    logger.setLevel(logging.DEBUG)
    ch = logging.StreamHandler()
    ch.setLevel(logging.DEBUG)

    # create formatter
    formatter = logging.Formatter(
        '%(asctime)s [%(levelname)s]:  %(message)s', '%H:%M:%S')
    ch.setFormatter(formatter)
    logger.addHandler(ch)

    host = '127.0.0.1'
    port = 8080
    app.run(host=host, debug=True, threaded=True, port=port)
    logger.info(f'API v1.0 online: http://{host}:{port}')
