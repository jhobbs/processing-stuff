Some interesting ODEs with linear coefficients.

We'll cover equations of the form:

    (ax + by + c)dx - (qx + ry + s)dy = 0

Those are interesting because they can be rewritten as:

    A = ax + by + c
    Q = qx + ry + s
    dy/dx = A/B = (ax + by +c)/(qx + ry + s)

That is, `A` gives the change in `y` and `Q` gives the change in `x`.

Solutions to these equations are functions of many shapes. So far,
I've seen:

* Straight Lines
* Parabolas
* Hyperbolas
* Spirals
* Ellipses?
* Circles?

At least - I've seen solutions drawn that are very close to those
shapes. Solving these equations to find out for sure what shapes
they are would be enlightening, but for now I'm just documenting
some of them.

These shapes can be translated around the plane by shifting up and down,
or by rotation, and maybe in other ways.

## Elliptical Spirals
This generates tight elliptical spirals titled on a 45 degree axis and centered around `(0.5, 1)`:

    (-5x + 3y + -1)dx - (-3x + 4y + -3)dy = 0

I'm not 100% sure if these are actual spirals, or ellipses that appear to spiral due to
error, but the solutions aren't clean ellipses so I think they really are spirals.

If we increase `a`, it results in elliptical spirals more stretched along the axis,
at least until `a = r` at `-3`.

Increasing `a` beyond `-3` here switches to generating hyperbolas, and maybe parabolas at
`a = -2`.

## Straight Lines

The easiest equation with straight line solutions is to generate is by setting all coefficients to `0`:

    (0x + 0y + 0)dx - (0x + 0y + 0)dy = 0

This makes the slope 0 everywhere, which means solutions are horizontal lines. There are no
equilibrium points - particles following these paths will move on the screen from the left
and off the screen to the right.

Closely related are two equations that also generate horizontal lines, but have equilibrium
points at the line `x = 0`. The first has `x = 0` as sources (particles move away from it):

    (0x + 0y + 0)dx - (1x + 0y + 0)dy = 0

And the second has `x = 0` as sinks (particles move towards it):

    (0x + 0y + 0)dx - (1x + 0y + 0)dy = 0

Actually, these hold for any value of `r`, which is clear because dividing the equation by
the value of `r` always results in `r = 1` with all other coefficients remaining at `0`.

# Limitations
This approach to understanding ODEs geometrically is super cool but has some limitations.
## Paths Stop at the Edge of the View
One of the limitations of my current approach is that I stop tracing paths when
a path goes offscreen - this can make what might be an elliptical shape appear to
be parabolic, for example.

I could remedy this by just drawing to some max length instead of stopping at the
edge of screens.

## Error in Particle Movement
Integral curves are drawn by moving a particle in the direction given by the direction field
a tiny amount and then repeating. Unless that tiny amount is infinitely small, that necessarily
creates some error as the movement is a linear interpolation of the direction vector in the area
near the particle. That error accumulates over time as the particle moves.

One remedy is to make the distance very small, but that creates a lot of work by requiring the
movement to be calculated many times.

I could remedy this by using a numerical ODE solver to find a more precise function for each
integral curve. That would still be an estimate, but it shouldn't accumulate error in the same
fashion.

## Stuck Particles (Equilibrium Points)
Particles become stuck if they reach a point where the direction vector switches diametrically.
Imagine the direction switches from pure rightward movement to pure leftward movement at some
point. A particle, starting on the left of the point, will move over that point to the right,
where the direction vector changes to point back to the left. On its next move, it will move
back to the left of the point. This will repeat forver - the point acts as a sink for the
particle. 

This happens at equilibrium points. On one hand, they are useful to highlight because they're
characteristic of the differential equation. On the other hand, they're annoying for tracing
paths, because particles can't pass them.

I've implemented code to detect stuck points by keeping track of recent movement and making
sure it's greater than some epsilon value. I'm sure I could solve for equilibrium points
somehow too.

If I wanted to make the paths continue, I'd have to detect these sinks and move past them.
Sometimes, that's possible, like when there is a line where many points occur, for example,
when the coefficients are parallel lines:

    (1x + 1y + 1)dx - (1x + 1y + 1)dy = 0

However, sometimes crossing the sink point can lead to many other paths, such as when it is
the center of a spiral:

    (-1x + 1y + 1)dx - (1x + 1y + 1)dy = 0

One more note here. When I trace paths, I do so in two parts:

1. Move a particle along the curve in the direction of the direction vector until we get stuck or leave the screen.
2. Move a particle along the curve in the *opposite* direction of the diretion vector, again until we get stuck or leave the screen.

Because of this, we also get stuck particles at source points, not just sink points.

Both sources and sinks are equilibrium points - sinks are stable and sources are unstable, and both get us stuck in the
same way because we sort of move backwards and forwards in time.