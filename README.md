# pdesolver
A simple implementation of the finite element method solve parabolic partial differential equations

An example is given in the Main file of the Project. 

The weak form must be chosen in a way, that the derivative at the boundary is zero, since no integration over the boundary happens in code.
The values of at the boundarys are also not allowed to be forced to zero, since the linear systems solver doesn't account for that case.

Example heat equation:

$$
-\int_{\Omega} \nabla u \cdot \nabla d\omega= \int_{\Omega} \frac{d}{dt}uvd\omega
$$

It currently supports only parabolic pdes which leads to problems of the form

$$
M \frac{d}{dt} u = Ku
$$

The resulting system of linear equations is currently solved with a simple gauss elimination(https://en.wikipedia.org/wiki/Gaussian_elimination#Pseudocode).

The mesh is currently only a simple square subdivided with the delaunay algorithm using https://github.com/locationtech/jts.

The code for multithreaded matrix multiplication might be very ugly, however it halfs the time it takes for one time step.
