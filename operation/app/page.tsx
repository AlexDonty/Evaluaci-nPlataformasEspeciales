'use client';

import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Table from 'react-bootstrap/Table';
import Pagination from 'react-bootstrap/Pagination';
import { encryptAES } from './encryptAES';

export default function Home() {

  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');

  const [operacion, setOperacion] = useState('');
  const [importe, setImporte] = useState('');
  const [cliente, setCliente] = useState('');
  const [secreto, setSecreto] = useState('');
  const [visible, setVisible] = useState(false);
  const [data, setData] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);


  const getOperations = async (pageNumber: number) => {
    try {

      const response = await fetch(
        `http://localhost:8081/api/v1?page=${pageNumber}&size=10&sortBy=id&direction=ASC`
      );

      if (!response.ok) {
        alert('Error al obtener las operaciones: ' + response.statusText);
      } else {
        const data = await response.json();
        setData(data.content);
        setTotalPages(data.totalPages);
        setPage(data.number);
      }
    } catch (error) {
      console.error(error);
    }
  }

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    const payload = {
      user,
      password
    };

    try {

      const response = await fetch(
        'http://localhost:8081/api/v1/user',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(payload),
        }
      );

      const data = await response.json();
      if (!response.ok) {
        alert('Login fallido: ' + data.message);
      } else {
        console.log('Login exitoso:', data);
        setVisible(true);
        getOperations(0);
      }

    } catch (error) {
      console.error('Error en login:', error);
    }
  };

  const handleAddOperation = async (e: React.FormEvent) => {
    e.preventDefault();

    const secretKey = 'iOxh7eyMRdsTHGDzqikeKQdvUqRmqaJ5Gn0eWvKcqsU='
    const secretoCifrado = await encryptAES(secreto, secretKey);

    const payload = {
      operacion,
      importe,
      cliente,
      secreto: secretoCifrado
    };

    try {


      const response = await fetch(
        'http://localhost:8080/api/v1/operation',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(payload),
        }
      );

      if (!response.ok) {
        alert('Error al registrar la operación: ' + response.statusText);
      } else {
        getOperations(0);
      }

    } catch (error) {
      console.error('Error al registrar la operación:', error);
    }
  };



  return (
    <div
      className="d-flex justify-content-center"
      style={{ paddingTop: '60px' }}
    >
      {!visible && (
        <Card
          className="shadow"
          style={{ width: '380px' }}
        >
          <Card.Body>

            <Card.Title className="text-center mb-4">
              Login
            </Card.Title>

            <Form onSubmit={handleLogin}>

              <Form.Group className="mb-3">
                <Form.Label>Usuario</Form.Label>

                <Form.Control
                  type="text"
                  placeholder="Ingresa tu usuario"
                  value={user}
                  onChange={(e) => setUser(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-4">
                <Form.Label>Password</Form.Label>

                <Form.Control
                  type="password"
                  placeholder="Ingresa tu contraseña"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </Form.Group>

              <div className="d-grid">
                <Button
                  variant="primary"
                  type="submit"
                >
                  Login
                </Button>
              </div>

            </Form>

          </Card.Body>
        </Card>
      )}

      {visible && (
        <Card
          className="shadow"
          style={{ width: '500px' }}
        >
          <Card.Body>

            <Card.Title className="text-center mb-4">
              Ventana Registrar operación
            </Card.Title>

            <Form onSubmit={handleAddOperation}>

              <Form.Group className="mb-3">
                <Form.Label>Operación</Form.Label>

                <Form.Control
                  type="text"
                  placeholder="Ingresa la operación"
                  value={operacion}
                  onChange={(e) => setOperacion(e.target.value)}
                  required
                />
              </Form.Group>


              <Form.Group className="mb-3">
                <Form.Label>Importe</Form.Label>

                <Form.Control
                  type="text"
                  placeholder="Ingresa el importe"
                  value={importe}
                  onChange={(e) => setImporte(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Cliente</Form.Label>

                <Form.Control
                  type="text"
                  placeholder="Ingresa el cliente"
                  value={cliente}
                  onChange={(e) => setCliente(e.target.value)}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Secreto</Form.Label>

                <Form.Control
                  type="text"
                  placeholder="Ingresa el secreto"
                  value={secreto}
                  onChange={(e) => setSecreto(e.target.value)}
                  required
                />
              </Form.Group>



              <div className="d-grid">
                <Button
                  variant="primary"
                  type="submit"
                >
                  Guardar
                </Button>
              </div>

            </Form>

          </Card.Body>
        </Card>
      )}

      {visible && (
        <Card
          className="shadow"
          style={{ width: '500px' }}
        >
          <Card.Body>

            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Estatus</th>
                  <th>Referencia</th>
                  <th>Operación</th>
                </tr>
              </thead>

              <tbody>
                {data.map((item: any) => (
                  <tr key={item.id}>
                    <td>{item.id}</td>
                    <td>{item.estatus}</td>
                    <td>{item.referencia}</td>
                    <td>{item.operacion}</td>
                  </tr>
                ))}

              </tbody>
            </Table>

            <Pagination className="justify-content-center">

              <Pagination.Prev
                disabled={page === 0}
                onClick={() => setPage(page - 1)}
              />

              {Array.from({ length: totalPages }, (_, index) => (
                <Pagination.Item
                  key={index}
                  active={index === page}
                  onClick={() => setPage(index)}
                >
                  {index + 1}
                </Pagination.Item>
              ))}

              <Pagination.Next
                disabled={page === totalPages - 1}
                onClick={() => setPage(page + 1)}
              />

            </Pagination>


          </Card.Body>
        </Card>
      )}
    </div>
  );
}