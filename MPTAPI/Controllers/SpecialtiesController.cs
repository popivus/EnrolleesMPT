using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MPTAPI.Data;
using MPTAPI.Models;

namespace MPTAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SpecialtiesController : ControllerBase
    {
        private readonly EnrolleesDBContext _context;

        public SpecialtiesController(EnrolleesDBContext context)
        {
            _context = context;
        }

        // GET: api/Specialties
        [HttpGet]
        public IEnumerable<Specialties> GetSpecialties()
        {
            return _context.Specialties;
        }

        // GET: api/Specialties/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetSpecialties([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var specialties = await _context.Specialties.FindAsync(id);

            if (specialties == null)
            {
                return NotFound();
            }

            return Ok(specialties);
        }

        // PUT: api/Specialties/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutSpecialties([FromRoute] int id, [FromBody] Specialties specialties)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != specialties.ID_Speciality)
            {
                return BadRequest();
            }

            _context.Entry(specialties).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SpecialtiesExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Specialties
        [HttpPost]
        public async Task<IActionResult> PostSpecialties([FromBody] Specialties specialties)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Specialties.Add(specialties);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetSpecialties", new { id = specialties.ID_Speciality }, specialties);
        }

        // DELETE: api/Specialties/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSpecialties([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var specialties = await _context.Specialties.FindAsync(id);
            if (specialties == null)
            {
                return NotFound();
            }

            _context.Specialties.Remove(specialties);
            await _context.SaveChangesAsync();

            return Ok(specialties);
        }

        private bool SpecialtiesExists(int id)
        {
            return _context.Specialties.Any(e => e.ID_Speciality == id);
        }
    }
}