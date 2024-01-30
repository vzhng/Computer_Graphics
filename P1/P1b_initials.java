/******************************************************************************
Draw your initials below in perspective.
******************************************************************************/
//Veronica Zhang
void persp_initials()
{
  Init_Matrix();
  Perspective (20.0, 1.0, 100.0);
  
  Push_Matrix();
  Translate (-2.0, -2.0, 80.0);
  Scale (4.0, 4.0, 1.0);
  RotateY(50);  
  RotateX(-40);
  
  //V
  Begin_Shape();
  Vertex (-2.0, 1.0, 1.0);
  Vertex (-1.0, -1.0, 1.0);
  Vertex (-1.0, -1.0, 1.0);
  Vertex (0, 1.0, 1.0);

  //Z
  Vertex (0, 1.0, 1.0);
  Vertex (2.0, 1.0, 1.0);
  Vertex (2.0, 1.0, 1.0);
  Vertex (0.0, -1.0, 1.0);
  Vertex (0.0, -1.0, 1.0);
  Vertex (2.0, -1.0, 1.0);
  End_Shape();
  
  Pop_Matrix();
}