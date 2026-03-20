# GARender - Geometria Analítica 3D

O **GARender** é um motor de renderização 3D e uma ferramenta interativa de geometria analítica desenvolvida inteiramente do zero em Java. Sem depender de bibliotecas gráficas 3D externas, o projeto implementa a sua própria matemática de projeção em perspetiva, álgebra linear e renderização em ecrã através do Java Swing. 

Este projeto foi desenhado para facilitar a visualização e o cálculo de operações espaciais, servindo como uma ferramenta de apoio prático e visual, especialmente alinhada com os tópicos de Álgebra Linear e Geometria Analítica.

## Funcionalidades

* **Renderização 3D do Zero:** Implementação própria de projeções de perspetiva, transformando coordenadas esféricas e cartesianas do espaço 3D para o plano 2D.
* **Câmara Interativa:** Controlo de órbita fluido (arrastar com o rato) e zoom (roda do rato), permitindo a navegação livre pelo espaço tridimensional.
* **Entidades Geométricas:** Suporte total para visualização e manipulação de:
  * Pontos ( `Point3D` )
  * Vetores ( `Vector3D` )
  * Retas ( `Line3D` )
  * Planos ( `Plane3D` )
* **Motor de Cálculos Analíticos:** Uma interface dedicada (`OperationsPanel`) e um menu de contexto inteligente para calcular e visualizar instantaneamente:
  * Distâncias (Ponto a Ponto, Ponto a Reta, Ponto a Plano, Reta a Reta).
  * Ângulos (Entre retas, entre vetores, Reta a Plano).
  * Interseções (Reta com Plano, Plano com Plano).
  * Álgebra Vetorial (Soma, Subtração, Produto Escalar, Produto Vetorial, Projeção e Reflexão).
  * Projeções geométricas na cena visual.

## Estrutura do Projeto

A arquitetura do código está dividida em pacotes lógicos para separar a matemática pura da interface gráfica:

* `src.core`: Contém as bases matemáticas fundamentais (`Point3D`, `Vector3D`) gerindo precisões e tolerâncias de vírgula flutuante (Epsilon).
* `src.entities`: Define os elementos geométricos mais complexos construídos sobre o núcleo básico (`Line3D`, `Plane3D`) e o contentor principal (`Scene`).
* `src.camera`: Contém a lógica de transformação, matrizes da câmara e matemática de projeção em perspetiva.
* `src.gui`: Toda a interface gráfica interativa construída em Java Swing, incluindo o canvas de renderização (`RenderCanvas`), painéis de gestão e caixas de diálogo para criação de entidades.

## Como Executar

Como o projeto é escrito em Java puro (Standard Edition), não necessita de configurações complexas de dependências ou motores de jogo externos.

1. Certifica-te de que tens o **Java Development Kit (JDK)** instalado na tua máquina (preferencialmente versão 8 ou superior).
2. Clona o repositório ou descarrega os ficheiros de código-fonte.
3. Compila o projeto a partir do diretório raiz (onde a pasta `src` está localizada):
   ```bash
   javac -d bin src/*.java && java -cp bin src.Main
